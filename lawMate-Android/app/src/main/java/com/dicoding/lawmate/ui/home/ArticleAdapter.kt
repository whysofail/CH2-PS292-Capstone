
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.lawmate.data.Article
import com.dicoding.lawmate.databinding.NewsItemBinding
import com.dicoding.lawmate.ui.home.DetailArticleActivity

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.StoryViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)

        holder.itemView.setOnClickListener {
            // Mengirim ID article ke activity lain menggunakan Intent
            val context = holder.itemView.context
            val intent = Intent(context, DetailArticleActivity::class.java)
            intent.putExtra("judul", article.judul)
            intent.putExtra("description", article.description)
            intent.putExtra("images", article.images)
            intent.putExtra("date", article.date)

            context.startActivity(intent)

        }
    }

    class StoryViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvJudul.text = article.judul
            binding.tvDesc.text = article.description
            binding.tvDate.text = article.date

            Glide.with(binding.root)
                .load(article.images)
                .into(binding.imgStory)
        }

    }

    // DiffCallback untuk membandingkan objek artikel
    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
