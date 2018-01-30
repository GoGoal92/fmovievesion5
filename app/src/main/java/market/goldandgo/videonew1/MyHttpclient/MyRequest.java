package market.goldandgo.videonew1.MyHttpclient;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import market.goldandgo.videonew1.Choicetoview;
import market.goldandgo.videonew1.Coingame;
import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.EditMovies;
import market.goldandgo.videonew1.FakeDialog;
import market.goldandgo.videonew1.Fragment.Fragment_Home;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.Fragment.Fragment_Series;
import market.goldandgo.videonew1.Freegold;
import market.goldandgo.videonew1.Mymovies;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.Readmore;
import market.goldandgo.videonew1.Search;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Splash;
import market.goldandgo.videonew1.Utils.Encode_Decoder;
import market.goldandgo.videonew1.Utils.Stringconverter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class MyRequest {

    static long sec = 10;
    private static Object userinfocoin;


    public static void checkversion() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Checkversion(Constant.apikey, phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Splash.Feedback(response.body().toString());
                } catch (Exception e) {
                    Splash.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Splash.Feedback_Error();

                t.printStackTrace();
            }
        });
    }

    public static void getsearchmovie(String s,String text) {


        String base6d= Encode_Decoder.encoding_string(text);
        base6d=base6d.replace("\n","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getsearchmovie(Constant.apikey, s,text,base6d,phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                try {

                    Search.Feedback(response.body().toString());
                } catch (Exception e) {
                    Search.Feedback_Error();
                    e.printStackTrace();

                }



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Search.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void Editmovies(String title, String detail, String link, String cate, String mid) {


        title = Encode_Decoder.encoding_string(title);
        detail = Encode_Decoder.encoding_string(detail);
        link = Encode_Decoder.encoding_string(link);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Editmovies(Constant.apikey, title,detail,link,cate,mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                EditMovies.Feedback(response.body().toString());


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static void getreadmore(String a) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getreadmore(Constant.apikey, a);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Readmore.Feedback(response.body().toString());
                } catch (Exception e) {
                    Readmore.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Readmore.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void getcategory_series(String mid, String s) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getcategory_series(Constant.apikey, mid, s);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    SeriesDetail.Feedback(response.body().toString());
                } catch (Exception e) {
                    SeriesDetail.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                SeriesDetail.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void userlike(String mid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.userlike(Constant.apikey, phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public static void getMoviedetail(String mid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getMoviedetail(Constant.apikey, phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Detail.Feedback(response.body().toString());
                } catch (Exception e) {
                    Detail.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Detail.Feedback_Error();

                t.printStackTrace();
            }
        });

    }

    public static void getseriesdetail(String sid, String mid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseriesdetail(Constant.apikey, phoneid.getid(),sid,mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Detail.Feedback(response.body().toString());
                } catch (Exception e) {
                    Detail.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Detail.Feedback_Error();

                t.printStackTrace();
            }
        });
    }

    public static void getMyallmovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getMyallmovies(Constant.apikey, phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Mymovies.Feedback(response.body().toString());
                } catch (Exception e) {
                    Mymovies.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Mymovies.Feedback_Error();

                t.printStackTrace();
            }
        });
    }

    public static void gethdlink(String vid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host1).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.requesthdlink(vid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    FakeDialog.Facebookfeedback(response.body().toString());
                } catch (Exception e) {
                    FakeDialog.Feedback_fbError();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FakeDialog.Feedback_fbError();
                t.printStackTrace();
            }
        });
    }

    public static void Mainpage() {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(sec, TimeUnit.SECONDS)
                .readTimeout(sec, TimeUnit.SECONDS)
                .writeTimeout(sec, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Mainpage(Constant.apikey);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_Home.Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_Home.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Home.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void getseeallMovie(String s, String cate) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(sec, TimeUnit.SECONDS)
                .readTimeout(sec, TimeUnit.SECONDS)
                .writeTimeout(sec, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseeallMovie(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_Movie.Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_Movie.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Movie.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void getseeallseries() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(sec, TimeUnit.SECONDS)
                .readTimeout(sec, TimeUnit.SECONDS)
                .writeTimeout(sec, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.SeeallSeries(Constant.apikey,phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_Series.Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_Series.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Series.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void userreprt(String mid) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(sec, TimeUnit.SECONDS)
                .readTimeout(sec, TimeUnit.SECONDS)
                .writeTimeout(sec, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.userreprt(Constant.apikey, phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static void faosdonoaphin(String userid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.faosdonoaphin(Constant.apikey, userid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Freegold.Feedbackfaosdonoaphin(response.body().toString());
                } catch (Exception e) {
                    Freegold.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Freegold.Feedback_Error();
                t.printStackTrace();
            }
        });

    }


    public static void getuserinfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getuserinfo(Constant.apikey, phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Freegold.Feedback_resume(response.body().toString());
                } catch (Exception e) {
                    Freegold.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Freegold.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void buyseries(String buymid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.buyseries(Constant.apikey, phoneid.getid(),buymid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_Series.Feedback_buy(response.body().toString());
                } catch (Exception e) {
                    Fragment_Series.Feedback_buyError();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Series.Feedback_buyError();
                t.printStackTrace();
            }
        });

    }

    public static void buyseriessearch(String buymid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.buyseries(Constant.apikey, phoneid.getid(),buymid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Search.Feedback_buy(response.body().toString());
                } catch (Exception e) {
                    Search.Feedback_buyError();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Search.Feedback_buyError();
                t.printStackTrace();
            }
        });

    }


    public static void getuserinfocoin() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getuserinfo(Constant.apikey, phoneid.getid());
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Coingame.Feedback_resume(response.body().toString());
                } catch (Exception e) {
                    Coingame.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Coingame.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void coin(String userid, int userchoose, int userbet) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.coin(Constant.apikey, userid,userchoose+"",userbet+"");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Coingame.Feedbackfaosdonoaphin(response.body().toString());
                } catch (Exception e) {
                    Coingame.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Coingame.Feedback_Error();
                t.printStackTrace();
            }
        });
    }



    public static void getseeallMoviespinner(String s, String cate) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(sec, TimeUnit.SECONDS)
                .readTimeout(sec, TimeUnit.SECONDS)
                .writeTimeout(sec, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseeallMovie(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_Movie.Feedbackwithoutspinner(response.body().toString());
                } catch (Exception e) {
                    Fragment_Movie.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Movie.Feedback_Error();
                t.printStackTrace();
            }
        });
    }


    public static void gethdlinkfromwatch(String vid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host1).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.requesthdlink(vid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Choicetoview.Facebookfeedback(response.body().toString());
                } catch (Exception e) {
                    Choicetoview.Feedback_fbError();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Choicetoview.Feedback_fbError();
                t.printStackTrace();
            }
        });


    }
}
