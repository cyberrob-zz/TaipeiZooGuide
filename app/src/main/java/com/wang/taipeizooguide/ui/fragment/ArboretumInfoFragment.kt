package com.wang.taipeizooguide.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wang.taipeizooguide.R
import com.wang.taipeizooguide.data.model.Arboretum
import com.wang.taipeizooguide.ui.view.TitleDescriptionView
import com.wang.taipeizooguide.viewmodel.ArboretumInfoViewModel
import kotlinx.android.synthetic.main.fragment_arboretum_info.*
import kotlinx.android.synthetic.main.layout_recycler_view_adapter.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.full.memberProperties


class ArboretumInfoFragment : Fragment() {

    companion object {
        const val BUNDLE_ARBORETUM = "BUNDLE_ARBORETUM"
    }

    private val viewModel: ArboretumInfoViewModel by viewModel()

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_arboretum_info, container, false)
    }

    override fun onDestroyView() {
        dialog = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleDataFromArgument()?.also {
            populateArboretum(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Navigation.findNavController(this@ArboretumInfoFragment.requireView()).navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getBundleDataFromArgument(): Arboretum? {
        return requireArguments().getParcelable<Arboretum>(BUNDLE_ARBORETUM)
    }

    private fun populateArboretum(arboretum: Arboretum) {
        (activity as AppCompatActivity).supportActionBar?.title = arboretum.F_Name_En
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""

        Glide.with(arboretum_image)
            .load(arboretum.F_Pic01_URL)
            .apply(RequestOptions().centerCrop())
            .error(R.drawable.ic_pixeltrue_error)
            .into(arboretum_image)

        arboretum_name_zh?.text = arboretum.F_Name_En
        iarboretum_updated_at?.text = arboretum.F_Update

        content_container?.run {
            removeAllViews()

            Arboretum::class.memberProperties.filter { it.name in viewModel.classPropertyNameMap.keys }
                .forEach { member ->
                    viewModel.classPropertyNameMap[member.name]?.run stringResId@{
                        generateTitleDescriptionView(
                            title = getString(this@stringResId),
                            description = member.get(arboretum) as? String ?: "N/A"
                        ).also {
                            addView(it)
                        }
                    }
                }
        }
    }

    private fun generateTitleDescriptionView(
        title: String,
        description: String
    ): TitleDescriptionView {
        return TitleDescriptionView(context = requireContext()).apply {
            setup(title = title, description = description)
        }
    }
}