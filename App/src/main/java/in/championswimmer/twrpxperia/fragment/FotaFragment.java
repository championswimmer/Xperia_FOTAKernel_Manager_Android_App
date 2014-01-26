package in.championswimmer.twrpxperia.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.championswimmer.twrpxperia.R;
import in.championswimmer.twrpxperia.flashutils.FlashFota;
import in.championswimmer.twrpxperia.flashutils.SaveDir;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this in.championswimmer.twrpxperia.fragment must implement the
 * {@link FotaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FotaFragment#newInstance} factory method to
 * create an instance of this in.championswimmer.twrpxperia.fragment.
 *
 */
public class FotaFragment extends Fragment {

    private String LOG_TAG = "XRM FotaFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the in.championswimmer.twrpxperia.fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FlashFota ff;
    private Boolean backupExists = false;
    private Boolean hasRoot = false;

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
     * @return A new instance of in.championswimmer.twrpxperia.fragment FotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FotaFragment newInstance(String param1, String param2) {
        FotaFragment fragment = new FotaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FotaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SaveDir dir = new SaveDir();
        backupExists = dir.existsFotaBackup();
        ff = new FlashFota(getActivity().getApplicationContext());
        hasRoot = ff.hasRoot();

        Log.d(LOG_TAG, dir.backupBath());
        Log.d(LOG_TAG, dir.existsFotaBackup().toString());
        Log.d(LOG_TAG, dir.cwmPath());
        Log.d(LOG_TAG, dir.existsCwmImage().toString());
        Log.d(LOG_TAG, dir.twrpPath());
        Log.d(LOG_TAG, dir.existsTwrpImage().toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this in.championswimmer.twrpxperia.fragment

        View rootView = inflater.inflate(R.layout.fragment_fota, container, false);
        Button restoreButton = (Button) rootView.findViewById(R.id.fota_restore_button);
        Button backupButton = (Button) rootView.findViewById(R.id.fota_backup_button);
        Button formatButton = (Button) rootView.findViewById(R.id.fota_format_button);

        //disable restore button if backup does not exist
        restoreButton.setEnabled((backupExists) && (hasRoot));

        //disable format and restore button if root does not exist
        formatButton.setEnabled(hasRoot);

        //add onclick method for format
        formatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff.format();
            }
        });
        //add onclick method for backup
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff.backup();
            }
        });
        //add onclick method for restore
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ff.restore();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
