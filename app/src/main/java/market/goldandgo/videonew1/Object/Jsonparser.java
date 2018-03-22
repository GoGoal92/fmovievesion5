package market.goldandgo.videonew1.Object;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.calculate_st;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class Jsonparser {


    public static ArrayList<get> getserieslistrow(String s, String no) {
        ArrayList<get> strlist = new ArrayList<>();


        try {

            JSONObject nn = new JSONObject(s);

            JSONArray id = nn.getJSONArray("arr");
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setTitle(c1.getString("title"));
                eg.setMid(c1.getString("id"));
                eg.setEpisode(c1.getString("episode"));


                eg.setReportcount(c1.getString("reprot"));

                if (no.equals("0")) {
                    no = c1.getString("id");
                }


                if (no.equals(c1.getString("id"))) {
                    eg.setBaclgrouund(R.color.backcolorsolid);
                } else {
                    eg.setBaclgrouund(R.color.white);
                }


                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }


    public static ArrayList<get> getmymovies(String s) {
        ArrayList<get> list = new ArrayList<>();
        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setCategory(c1.getString("category"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(calculate_st.format(Long.parseLong(c1.getString("like"))));
                eg.setView(calculate_st.format(Long.parseLong(c1.getString("view"))));
                eg.setDetail(Constant.cleanstring(c1.getString("detail"), c1.getString("vstatus")));
                String path = Constant.moviephoto + c1.getString("image");
                eg.setImage(path);
                eg.setMurl(Constant.cleanstring(c1.getString("url"), c1.getString("vstatus")));
                eg.setCategory(c1.getString("cate"));

                list.add(eg);
            }


        } catch (Exception e) {

        }
        return list;
    }


    public static ArrayList<get> getMovierated(String s, String top) {


        ArrayList<get> list = new ArrayList<>();
        try {

            JSONObject jojo = new JSONObject(s);
            String toprated=jojo.getString("other");

            JSONObject otherobject = new JSONObject(toprated);

            JSONArray id = otherobject.getJSONArray(top);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(c1.getString("like"));
                eg.setView(c1.getString("view"));

                String path = "";
                if (c1.getString("mid").contains("s")) {
                    path = Constant.seriesrcoverphoto + c1.getString("image");

                } else {
                    path = Constant.moviephoto + c1.getString("image");
                }
                eg.setImage(path);

                list.add(eg);
            }


        } catch (Exception e) {

            Log.e("Jsonparese",e.toString());

        }
        return list;
    }


    public static ArrayList<get> getsearchalllist(String s) {
        ArrayList<get> list = new ArrayList<>();
        try {
            JSONObject jlo = new JSONObject(s);


            JSONArray id = jlo.getJSONArray("movie");
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setCategory(c1.getString("category"));
                eg.setReportcount(c1.getString("reprot"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(calculate_st.format(Long.parseLong(c1.getString("like"))));
                eg.setView(calculate_st.format(Long.parseLong(c1.getString("view"))));
                eg.setDetail(Constant.cleanstring(c1.getString("detail"), c1.getString("vstatus")));
                String path = Constant.moviephoto + c1.getString("image");
                eg.setImage(path);
                eg.setPrice(c1.getString("price"));
                eg.setMine(c1.getString("mine"));
                list.add(eg);
            }


        } catch (Exception e) {

            Log.e("jsonerror", e.toString());
        }
        return list;
    }

    public static ArrayList<get> getMoviealllist(String s) {
        ArrayList<get> list = new ArrayList<>();
        try {
            JSONObject jlo = new JSONObject(s);


            JSONArray id = jlo.getJSONArray("movie");
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setCategory(c1.getString("category"));
                eg.setReportcount(c1.getString("reprot"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(calculate_st.format(Long.parseLong(c1.getString("like"))));
                eg.setView(calculate_st.format(Long.parseLong(c1.getString("view"))));
                eg.setDetail(Constant.cleanstring(c1.getString("detail"), c1.getString("vstatus")));
                String path = Constant.moviephoto + c1.getString("image");
                eg.setImage(path);

                list.add(eg);
            }


        } catch (Exception e) {

            Log.e("jsonerror", e.toString());
        }
        return list;
    }

    public static ArrayList<get> getcatelist(String s) {
        ArrayList<get> list = new ArrayList<>();

        get eg1 = new get();
        eg1.setMid("0");
        eg1.setTitle("LATEST MOVIES");
        list.add(eg1);
        try {
            JSONObject jlo = new JSONObject(s);


            JSONArray id = jlo.getJSONArray("cate");
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setTitle(c1.getString("title"));
                list.add(eg);
            }


        } catch (Exception e) {

        }
        return list;
    }

    public static String getonestring(String str, String status) {
        JSONObject jo = null;
        String result = "";

        try {
            jo = new JSONObject(str);
            result = jo.getString(status);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static ArrayList<get> getseriesalllist(String s) {
        ArrayList<get> list = new ArrayList<>();
        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setCategory(c1.getString("category"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(calculate_st.format(Long.parseLong(c1.getString("like"))));
                eg.setView(calculate_st.format(Long.parseLong(c1.getString("view"))));
                eg.setDetail(Constant.cleanstring(c1.getString("detail"), c1.getString("vstatus")));
                String path = Constant.seriesrcoverphoto + c1.getString("image");
                eg.setImage(path);
                eg.setPrice(c1.getString("price"));
                eg.setMine(c1.getString("mine"));
                eg.setSeriescate(c1.getString("cat"));

                list.add(eg);
            }


        } catch (Exception e) {

        }
        return list;
    }

    public static ArrayList<get> getseriesalllist1(String s, String po) {
        ArrayList<get> list = new ArrayList<>();
        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                if (c1.getString("cat").equals(po)) {
                    get eg = new get();
                    eg.setMid(c1.getString("mid"));
                    eg.setCategory(c1.getString("category"));
                    eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                    eg.setLike(calculate_st.format(Long.parseLong(c1.getString("like"))));
                    eg.setView(calculate_st.format(Long.parseLong(c1.getString("view"))));
                    eg.setDetail(Constant.cleanstring(c1.getString("detail"), c1.getString("vstatus")));
                    String path = Constant.seriesrcoverphoto + c1.getString("image");
                    eg.setImage(path);
                    eg.setPrice(c1.getString("price"));
                    eg.setMine(c1.getString("mine"));
                    eg.setSeriescate(c1.getString("cat"));

                    list.add(eg);
                }
            }


        } catch (Exception e) {

        }
        return list;
    }


    public static ArrayList<get> getseriesrated(String s, String top) {
        ArrayList<get> list = new ArrayList<>();
        try {

            JSONObject jojo = new JSONObject(s);
            String toprated=jojo.getString("other");
            JSONObject otherobject = new JSONObject(toprated);
            JSONArray id = otherobject.getJSONArray(top);


            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setMid(c1.getString("mid"));
                eg.setTitle(Constant.cleanstring(c1.getString("title"), c1.getString("vstatus")));
                eg.setLike(c1.getString("like"));
                eg.setView(c1.getString("view"));
                eg.setPrice(c1.getString("price"));

                String path = "";
                if (c1.getString("mid").contains("s")) {
                    path = Constant.seriesrcoverphoto + c1.getString("image");

                } else {
                    path = Constant.moviephoto + c1.getString("image");
                }
                eg.setImage(path);
                eg.setEpisode(c1.getString("ep"));
                eg.setDetail("<b>" + c1.getString("detail") + "<b>");
                eg.setMine(c1.getString("mine"));
                list.add(eg);
            }


        } catch (Exception e) {

        }
        return list;
    }

    public static ArrayList<get> getadlist(String s) {
        ArrayList<get> list = new ArrayList<>();
        try {

            JSONObject jojo = new JSONObject(s);
            JSONArray id = jojo.getJSONArray("ads");
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg = new get();
                eg.setAdsid(c1.getString("id"));
                eg.setAdsurl(c1.getString("url"));
                String path = Constant.datalocation_ads + c1.getString("id")+".fmovie";
                eg.setAdsimage(path);
                list.add(eg);
            }


        } catch (Exception e) {

        }
        return list;
    }
}
