package com.mandy.innfedia.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.ApiInterface;
import com.mandy.innfedia.ApiModel.GetAddToCart;
import com.mandy.innfedia.ApiModel.GetProductDetails;
import com.mandy.innfedia.ApiModel.GetProductList;
import com.mandy.innfedia.MainActivity;
import com.mandy.innfedia.R;
import com.mandy.innfedia.ServiceGenerator;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.Utils.ProgressBarClass;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.mandy.innfedia.adapter.ColorAdapter;
import com.mandy.innfedia.adapter.SizeAdapter;
import com.mandy.innfedia.adapter.ViewPagerProductImageAdapter;
import com.mandy.innfedia.adapter.main2.SeeRelatedItemAdapter;


import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    RecyclerView recyclerViewSize, recyclerViewColor, recyclerViewSeeRelated;
    TextView textColor, textSize, txtPrice, txtOfferPrice, txtRatingNumber, txtProductName, txtProductSubName;
    ReadMoreTextView txtDescription, txtFeature;
    Button btnAddtoCart, btnBuy;
    View view;
    FragmentManager manager;
    ArrayList<Integer> arrayImage = new ArrayList<>();
    ArrayList<GetProductList.Datum> arrayList = new ArrayList<>();
    RatingBar ratingBar;
    LinearLayout linearLayout;
    String id;
    SharedToken sharedToken;
    Context context;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_details, container, false);

        manager = getActivity().getSupportFragmentManager();

        init();


        sharedToken = new SharedToken(context);


        MainActivity.textView.setText("Product Details");

        if (CheckInternet.isInternetAvailable(context)) {
            getData();
            //see the related item
            getRelatedItem();
        } else {
            context.startActivity(new Intent(context, NoInternetActivity.class));
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.framelayout, new CommentFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.isInternetAvailable(context)) {
                    addToCart();
                } else {
                    context.startActivity(new Intent(context, NoInternetActivity.class));
                }
            }
        });
        return view;

    }


    private void init() {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleindecator);
        recyclerViewSize = (RecyclerView) view.findViewById(R.id.recyclerSize);
        recyclerViewSeeRelated = (RecyclerView) view.findViewById(R.id.recyclerRelated);
        recyclerViewColor = (RecyclerView) view.findViewById(R.id.recyclerColor);

        textColor = (TextView) view.findViewById(R.id.txtColor);
        textSize = (TextView) view.findViewById(R.id.txtSize);
        txtDescription = (ReadMoreTextView) view.findViewById(R.id.perview_description);
        txtFeature = (ReadMoreTextView) view.findViewById(R.id.txtFeture);
        txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        txtOfferPrice = (TextView) view.findViewById(R.id.txtMRP);
        txtRatingNumber = (TextView) view.findViewById(R.id.txtratingNumber);
        txtProductName = (TextView) view.findViewById(R.id.txtproductName);
        txtProductSubName = (TextView) view.findViewById(R.id.txtSubProductName);


        ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear);

        btnAddtoCart = (Button) view.findViewById(R.id.btnAddCart);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("subId");
            Toast.makeText(context, "" + id, Toast.LENGTH_SHORT).show();
        }

    }


    //get the prduct details
    private void getData() {
        SharedToken sharedToken = new SharedToken(context);
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductDetails> call = apiInterface.getProductDetails("Bearer " + sharedToken.getShared(), id);
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        call.enqueue(new Callback<GetProductDetails>() {
            @Override
            public void onResponse(Call<GetProductDetails> call, Response<GetProductDetails> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            GetProductDetails.Datum datum = response.body().getData().get(i);

                            txtProductName.setText(datum.getTitle());
                            txtProductSubName.setText(datum.getSlug());
                            txtRatingNumber.setText(datum.getTotalUsers().toString());
                            txtPrice.setText(Config.GET_RUPPESS_SYMBOL + datum.getSpecialPrice());
                            txtDescription.setText(datum.getDescription());
                            txtFeature.setText(datum.getFeatures());
                            txtOfferPrice.setText(Config.GET_RUPPESS_SYMBOL + datum.getPrice());
                            ratingBar.setRating(Float.parseFloat(datum.getAvgRating()));

                            setViewPager(response.body().getData().get(i).getProductImages());
                            setSize(response.body().getData().get(i).getSizes());
                            setColor(response.body().getData().get(i).getColors());
                        }

                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProductDetails> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    // set view pager
    private void setViewPager(List<GetProductDetails.ProductImage> arrayList) {
        PagerAdapter adapter = new ViewPagerProductImageAdapter(context, arrayList);
        viewPager.setAdapter(adapter);

        circleIndicator.setViewPager(viewPager);

        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

    }


    //set the size
    private void setSize(final List<GetProductDetails.Size> arrayList) {
        SizeAdapter adapter = new SizeAdapter(context, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSize.setLayoutManager(linearLayoutManager);
        recyclerViewSize.setAdapter(adapter);
        recyclerViewSize.addItemDecoration(new SpacesItemDecoration(10));
        adapter.setOnItemClick(new ColorAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                textSize.setText(arrayList.get(position).getSize());
            }
        });
    }


    //set the color
    private void setColor(final List<GetProductDetails.Color> arrayList) {
        ColorAdapter colorAdapter = new ColorAdapter(context, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewColor.setLayoutManager(linearLayoutManager);
        recyclerViewColor.setAdapter(colorAdapter);
        recyclerViewColor.addItemDecoration(new SpacesItemDecoration(10));

        colorAdapter.setOnItemClick(new ColorAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                textColor.setText(arrayList.get(position).getSize());
            }
        });

    }


    // see the relted product


    @Override
    public void onAttach(Context context1) {
        super.onAttach(context1);
        context = context1;
    }


    //get the releted item list
    private void getRelatedItem() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetProductList> call = apiInterface.getRelatedList("Bearer " + sharedToken.getShared(), ProductListFragment.id);
        call.enqueue(new Callback<GetProductList>() {
            @Override
            public void onResponse(Call<GetProductList> call, Response<GetProductList> response) {
                if (response.isSuccessful()) {
                    arrayList.clear();
                    if (response.body().getStatus().equals(200)) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            arrayList.add(response.body().getData().get(i));
                            setRelatedData();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProductList> call, Throwable t) {

            }
        });

    }


    private void setRelatedData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSeeRelated.setLayoutManager(linearLayoutManager);
        SeeRelatedItemAdapter adapter = new SeeRelatedItemAdapter(context, arrayList, manager);
        recyclerViewSeeRelated.setAdapter(adapter);
        recyclerViewSeeRelated.addItemDecoration(new SpacesItemDecoration(10));
    }


    //add to cart
    private void addToCart() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<GetAddToCart> cartCall = apiInterface.addToCart("Bearer " + sharedToken.getShared(), id);
        final Dialog dialog = ProgressBarClass.showProgressDialog(context);
        dialog.show();
        cartCall.enqueue(new Callback<GetAddToCart>() {
            @Override
            public void onResponse(Call<GetAddToCart> call, Response<GetAddToCart> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getData().equals(200)) {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                        MainActivity.txtcartNumber.setText(response.body().getData().getTotalCartProducts().toString());
                    } else {
                        Snack.snackbar(getActivity(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddToCart> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
