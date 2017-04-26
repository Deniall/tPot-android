package com.cpssd.organizr.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.activity.RecipeDetailActivity;
import com.cpssd.organizr.activity.SpecialOffersActivity;
import com.cpssd.organizr.adapters.RecipeAdapter;
import com.cpssd.organizr.adapters.RecyclerItemClickListener;
import com.cpssd.organizr.pojos.Finance.BalanceUpdate;
import com.cpssd.organizr.pojos.Finance.Details;
import com.cpssd.organizr.pojos.Finance.Input;
import com.cpssd.organizr.pojos.Recipes.Recipe;
import com.cpssd.organizr.pojos.Recipes.RecipeDetail;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "RecipesFragment";
    private static final String RECIPE_SEARCH_URL = "http://tpot.space:88/recipes/search/";

    Context mContext;
    public String query;

    private String mParam1;
    private String mParam2;

    public String SUB;

    EditText editText;
    Button queryButton;
    ImageButton filterButton;

    TextView viewSpecialOffers;

    protected RelativeLayout progressBar;

    private OnFragmentInteractionListener mListener;
    protected RecyclerView recipeRecycler;
    protected RecipeAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;
    protected ArrayList<Recipe> mDataset;
    enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    protected RecipesFragment.LayoutManagerType mCurrentLayoutManagerType;

    public RecipesFragment() {
        // Required empty public constructor
    }


    public static RecipesFragment newInstance() {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("sub", "Error");
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        queryButton = (Button) rootView.findViewById(R.id.querybutton);
        editText = (EditText) rootView.findViewById(R.id.seachbar);
        filterButton = (ImageButton) rootView.findViewById(R.id.fragment_recipes_filter);
        viewSpecialOffers = (TextView) rootView.findViewById(R.id.fragment_recipes_special_offers);
        progressBar = (RelativeLayout) rootView.findViewById(R.id.recipesLoading);
        progressBar.setVisibility(View.GONE);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wrapInScrollView = true;
                new MaterialDialog.Builder(getContext())
                        .title("Filter Search")
                        .customView(R.layout.filter_recipes_dialog, wrapInScrollView)
                        .positiveText("Apply")
                        .icon(getResources().getDrawable(R.drawable.ic_filter))
                        .negativeText("Cancel")
                        .show();
            }
        });

        rootView.setTag(TAG);
        recipeRecycler = (RecyclerView) rootView.findViewById(R.id.recipe_recycler);
        recipeRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final Recipe newRecipe = mDataset.get(position);
                        PopupMenu popup = new PopupMenu(getContext(), view);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                             @Override
                                                             public boolean onMenuItemClick(MenuItem item) {

                                                                 if (String.valueOf(item.getTitle()).equals("Details")) {
                                                                     final Recipe newRecipe = mDataset.get(position);
                                                                     Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                                                                     intent.putExtra("recipeImage", String.valueOf(newRecipe.getImage()));
                                                                     intent.putExtra("recipeId", String.valueOf(newRecipe.getId()));
                                                                     startActivity(intent);
                                                                 }

                                                                 else {
                                                                     new updateBalanceFromMeal().execute(String.valueOf(newRecipe.getId()));
                                                                     SetDayParams params = new SetDayParams(newRecipe, SUB, String.valueOf(item.getTitle()).toLowerCase());
                                                                     PostRecipe postRecipe = new PostRecipe();
                                                                     postRecipe.execute(params);
                                                                     Toast.makeText(getContext(), "Assigned to " + String.valueOf(item.getTitle()), Toast.LENGTH_LONG).show();
                                                                     return true;
                                                                 }
                                                                 return true;
                                                             }
                    });


                        popup.inflate(R.menu.menu);
                        popup.show();

                    }
                }));

        viewSpecialOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SpecialOffersActivity.class);
                startActivity(intent);
            }
        });


        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = editText.getText().toString();
                query = query.replaceAll(" ", "+").toLowerCase();
                recipeRecycler.setVisibility(View.VISIBLE);
                viewSpecialOffers.setVisibility(View.GONE);
                new RetrieveRecipes().execute();
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = RecipesFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        // Set CustomAdapter as the adapter for RecyclerView

        return rootView;
    }



    public void setRecyclerViewLayoutManager(RecipesFragment.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        recipeRecycler.setLayoutManager(mLayoutManager);
        recipeRecycler.scrollToPosition(scrollPosition);
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private static class SetDayParams {
        Recipe recipe;
        String SUB;
        String day;

        SetDayParams(Recipe recipe, String SUB, String day) {
            this.recipe = recipe;
            this.SUB = SUB;
            this.day = day;
        }
    }


    class PostRecipe extends AsyncTask<SetDayParams, Void, Void> {

        protected void onPreExecute() {


        }

        protected Void doInBackground(SetDayParams... urls) {
            SetDayParams params = urls[0];
            new NetworkController().postDay(params.recipe, params.SUB, params.day);
            return null;
        }


    }

    class RetrieveRecipes extends AsyncTask<String, Void, ArrayList<Recipe>> {

        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);


        }

        protected ArrayList<Recipe> doInBackground(String... urls) {

                mDataset = new NetworkController().getRecipes(query, SUB);

            return mDataset;
        }

        protected void onPostExecute(final ArrayList<Recipe> recipeNames) {
            progressBar.setVisibility(View.GONE);
            RecipeAdapter adapter = new RecipeAdapter(recipeNames, getActivity());
            recipeRecycler.setAdapter(adapter);

        }

    }

    class updateBalanceFromMeal extends AsyncTask<String, Void, Void>{



        @Override
        protected Void doInBackground(String... params) {
            String id = params[0];
            RecipeDetail recipeDetail = new NetworkController().getRecipeDetail(id);
            float floatCost = recipeDetail.getPricePerServing();
            int cost = Math.round(floatCost);
            cost = cost-(2*cost);
            String summary = recipeDetail.getTitle();
            BalanceUpdate balanceUpdate = new BalanceUpdate();
            Details details = new Details();
            Input input = new Input();
            details.setSummary(summary);
            details.setAmount(cost);
            input.setDetails(details);
            input.setAmount(cost);
            LocalDate currentDate = LocalDate.now();
            int dateF = Integer.parseInt(String.valueOf(currentDate).replace("-",""));
            input.setDate(dateF);
            input.setWeekStart(dateF);
            balanceUpdate.setInput(input);
            balanceUpdate.setUserId(SUB);
            new NetworkController().UpdateBalance(balanceUpdate);
            return null;
        }


        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext, "Recipe cost assigned to day", Toast.LENGTH_SHORT).show();
        }
    }
}
