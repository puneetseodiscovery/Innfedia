package com.mandy.innfedia.Payment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mandy.innfedia.Activities.NoInternetActivity;
import com.mandy.innfedia.GetMeesageApi;
import com.mandy.innfedia.Controller.Controller;
import com.mandy.innfedia.MyCart.CartAdapter;
import com.mandy.innfedia.R;
import com.mandy.innfedia.SpacesItemDecoration;
import com.mandy.innfedia.TermsAndCondition.TermsActivity;
import com.mandy.innfedia.Utils.CheckInternet;
import com.mandy.innfedia.Utils.Config;
import com.mandy.innfedia.Utils.SharedToken;
import com.mandy.innfedia.Utils.Snack;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements Controller.BuyItemsList, Controller.IncreseItemQuantity, Controller.DeleteItems, Controller.GetCheckSome {

    @BindView(R.id.tooolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textPolicy)
    TextView textPolicy;
    @BindView(R.id.txtItem)
    TextView txtItem;
    @BindView(R.id.txtDelivery)
    TextView txtDelivery;
    @BindView(R.id.txtOrderTotal)
    TextView txtOrderTotal;
    @BindView(R.id.reyclerPayment)
    RecyclerView reyclerPayment;
    @BindView(R.id.scrool_view)
    NestedScrollView scroolView;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.btnPayment)
    Button btnPayment;
    @BindView(R.id.txtItemQuantity)
    TextView txtItemQuantity;

    Controller controller;
    SharedToken sharedToken;
    String token, address, Cid, userId, totalPrice, website, callbackUrl, orderId, checkSomeCode;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        controller = new Controller((Controller.BuyItemsList) this, (Controller.IncreseItemQuantity) this, (Controller.DeleteItems) this, (Controller.GetCheckSome) this);

        sharedToken = new SharedToken(this);
        dialog = new Dialog(this);
        dialog.show();

        token = "Bearer " + sharedToken.getShared();
        userId = sharedToken.getUserId();
        address = getIntent().getStringExtra("Address");
        Cid = getIntent().getStringExtra("Cid");
        txtAddress.setText(address);

        controller.getBuyItemsList(token, Cid);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        textView.setText("Payment");

        //get terms and condition
        passIntent();


    }

    //click event on textView click
    private void passIntent() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.policy));

        //click for privicy
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(PaymentActivity.this, TermsActivity.class);
                intent.putExtra("T", "T");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };


        // click for condition
        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(PaymentActivity.this, TermsActivity.class);
                intent.putExtra("T", "C");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ss.setSpan(clickableSpan, 45, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(privacy, 64, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textPolicy.setHighlightColor(Color.TRANSPARENT);
        textPolicy.setText(ss, TextView.BufferType.SPANNABLE);
        textPolicy.setText(ss);
        textPolicy.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public void onSucess(Response<PaymentProductApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            PaymentProductApi.Data data = response.body().getData();
            txtItemQuantity.setText("items( " + data.getTotalProducts() + " ):");
            txtItem.setText(data.getCartPrice().toString());
            txtDelivery.setText(data.getDeliveryCharges().toString());
            txtOrderTotal.setText(data.getTotalCartPrice().toString());
            totalPrice = data.getTotalCartPrice().toString();

            ArrayList<PaymentProductApi.TotalCartProduct> products = new ArrayList<>();
            for (int i = 0; i < response.body().getData().getTotalCartProducts().size(); i++) {
                products.add(response.body().getData().getTotalCartProducts().get(i));
                setData(products);
            }
        } else {
            Snack.snackbar(PaymentActivity.this, response.body().getMessage());
        }
    }

    @Override
    public void error(String error) {
        dialog.dismiss();
        Snack.snackbar(PaymentActivity.this, error);
    }


    private void setData(ArrayList<PaymentProductApi.TotalCartProduct> products) {
        PaymentItemsAdapter adapter = new PaymentItemsAdapter(this, products);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reyclerPayment.setLayoutManager(linearLayoutManager);
        reyclerPayment.setAdapter(adapter);
        reyclerPayment.addItemDecoration(new SpacesItemDecoration(10));

        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String number, String id) {
                dialog.show();
                controller.setIncreseItemQuantity(token, id, number);
            }
        });

        adapter.setOnItemdelte(new CartAdapter.OnItemClickDelete() {
            @Override
            public void onItemDelete(String id) {
                dialog.show();
                controller.setDeleteItems(token, id);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    // for add quaninty
    @Override
    public void onSucessAdd(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(PaymentActivity.this, response.body().getMessage());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Snack.snackbar(PaymentActivity.this, response.body().getMessage());

        }
    }

    @Override
    public void Adderror(String error) {
        dialog.dismiss();
        Snack.snackbar(PaymentActivity.this, error);
    }

    //delete the item
    @Override
    public void onSuccessDelete(Response<GetMeesageApi> response) {
        dialog.dismiss();
        if (response.body().getStatus() == 200) {
            Snack.snackbar(PaymentActivity.this, response.body().getMessage());
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Snack.snackbar(PaymentActivity.this, response.body().getMessage());

        }
    }

    @Override
    public void errorDelete(String error) {
        dialog.dismiss();
        Snack.snackbar(PaymentActivity.this, error);
    }

    @OnClick(R.id.btnPayment)
    public void onViewClicked(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("'DS'-DyykmsS", Locale.ENGLISH);
        orderId = df.format(c.getTime());
        website = "WEBSTAGING";
        callbackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderId;
        if (CheckInternet.isInternetAvailable(this)) {
            dialog.show();
            controller.setGetCheckSome(token, Config.GET_MID, orderId, userId, "Retail", "WAP", totalPrice, website, callbackUrl);
        } else {
            startActivity(new Intent(this, NoInternetActivity.class));
        }
    }

    @Override
    public void getCheck(Response<ResponseBody> respose) {
        dialog.dismiss();
        try {
            checkSomeCode = respose.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openPaytm(checkSomeCode);
    }

    @Override
    public void gotError(String error) {
        dialog.dismiss();

    }

    // open paytm get way
    private void openPaytm(String checkSomeCode) {

        PaytmPGService service = PaytmPGService.getStagingService();
        HashMap<String, String> paraMap = new HashMap<>();
        paraMap.put("MID", Config.GET_MID);
        paraMap.put("ORDER_ID", orderId);
        paraMap.put("CUST_ID", userId);
        paraMap.put("CHANNEL_ID", "WAP");
        paraMap.put("TXN_AMOUNT", totalPrice);
        paraMap.put("WEBSITE", website);
        // This is the staging value. Production value is available in your dashboard
        paraMap.put("INDUSTRY_TYPE_ID", "Retail");
        // This is the staging value. Production value is available in your dashboard
        paraMap.put("CALLBACK_URL", callbackUrl);
        paraMap.put("CHECKSUMHASH", checkSomeCode);

        PaytmOrder Order = new PaytmOrder(paraMap);
        service.initialize(Order, null);

        service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                Log.d("+++++++++++++", inResponse.toString());
            }

            @Override
            public void networkNotAvailable() {

            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {

            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {

            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

            }
        });


    }
}


