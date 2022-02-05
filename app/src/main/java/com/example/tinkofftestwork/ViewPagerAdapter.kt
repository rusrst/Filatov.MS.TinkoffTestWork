import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tinkofftestwork.view.HotPresenter
import com.example.tinkofftestwork.view.LatestPresenter
import com.example.tinkofftestwork.view.TopPresenter

private const val NUM_TABS = 3

public class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
       when (position) {
            2 -> return HotPresenter()
            1 -> return TopPresenter()
        }
        return LatestPresenter()
    }
}