package com.appteam.catchup.ui.eventList


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.appteam.catchup.firebase.service.EventService
import com.appteam.catchup.model.Event
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_event_list.*
import java.util.*

class EventListFragment : Fragment() {

    private lateinit var adapter: Adapter
    private lateinit var navController: NavController
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = findNavController()
        adapter = Adapter(ArrayList<Event>().toMutableList(), navController)
        return inflater.inflate(com.appteam.catchup.R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        adapter.setItemsFilter { true }
                    }
                    1 -> {
                        adapter.setItemsFilter { it.members!!.filter { it.username.equals(mAuth.currentUser!!.displayName) }.isNotEmpty()   }
                    }
                    2 -> {
                        adapter.setItemsFilter { it.owner.username == mAuth.currentUser!!.displayName }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity!!.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        recycler_view.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recycler_view.adapter = adapter

        EventService.eventProvider().addOnCompleteListener {
            it.result!!.forEach {
                adapter.addItem(EventService.createEventFromDocuments(it))
            }
        }
    }

}
