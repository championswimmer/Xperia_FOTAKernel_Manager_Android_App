package in.championswimmer.twrpxperia.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.championswimmer.twrpxperia.R;
import in.championswimmer.twrpxperia.flashutils.FlashFota;
import in.championswimmer.twrpxperia.flashutils.GetImg;
import in.championswimmer.twrpxperia.flashutils.SaveDir;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this in.championswimmer.twrpxperia.fragment must implement the
 * {@link CwmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CwmFragment#newInstance} factory method to
 * create an instance of this in.championswimmer.twrpxperia.fragment.
 */
public class CwmFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the in.championswimmer.twrpxperia.fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String HAS_ROOT_PREF = "hasRoot";

    private FlashFota ff;
    private Boolean hasRoot;
    private Boolean cwmExists;
    private SaveDir dir;
    private GetImg gi;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this in.championswimmer.twrpxperia.fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of in.championswimmer.twrpxperia.fragment CwmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CwmFragment newInstance(String param1, String param2) {
        CwmFragment fragment = new CwmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CwmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dir = new SaveDir();
        cwmExists = dir.existsCwmImage();
        ff = new FlashFota(getActivity().getApplicationContext());
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        hasRoot = pref.getBoolean(HAS_ROOT_PREF, false);
        gi = new GetImg(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this in.championswimmer.twrpxperia.fragment
        final View rootView = inflater.inflate(R.layout.fragment_cwm, container, false);
        final Button downloadButton = (Button) rootView.findViewById(R.id.cwm_download_button);
        Button flashButton = (Button) rootView.findViewById(R.id.cwm_flash_button);

        //enable flash only if cwm.img exists
        flashButton.setEnabled((cwmExists) && (hasRoot));

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dir.validCwm()) {
                    ff.flashimg(dir.RAW_CWM_PATH);
                } else {
                    AlertDialog.Builder ad = new AlertDialog.Builder(inflater.getContext());
                    ad.setTitle("WARNING");
                    ad.setMessage(getResources().getString(R.string.alert_invalid_cwm_image));
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    ad.setPositiveButton("Flash anyway", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ff.flashimg(dir.RAW_CWM_PATH);
                        }
                    });
                    ad.show();
                }
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gi.downloadCWM();
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * in.championswimmer.twrpxperia.fragment to allow an interaction in this in.championswimmer.twrpxperia.fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
