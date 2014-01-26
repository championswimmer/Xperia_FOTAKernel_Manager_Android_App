package in.championswimmer.twrpxperia.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.championswimmer.twrpxperia.R;
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

    private Boolean cwmExists;

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
        SaveDir dir = new SaveDir();
        cwmExists = dir.existsCwmImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this in.championswimmer.twrpxperia.fragment
        View rootView = inflater.inflate(R.layout.fragment_cwm, container, false);
        Button flashButton = (Button) rootView.findViewById(R.id.cwm_flash_button);
        flashButton.setEnabled(cwmExists);

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
