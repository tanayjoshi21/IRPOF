package com.example.irpof.adapter

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.irpof.DataClass.Pdf
import com.example.irpof.databinding.LayoutPdfBinding

class PdfAdapter(private val context: Context,) :RecyclerView.Adapter<PdfAdapter.ViewHolder>(){

    private var items = listOf<Pdf>()


    fun setData(pdf :List<Pdf>){
        items = pdf
        Log.d("setData: ", items.size.toString())
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutPdfBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
       holder.onBind(items[position].fileName)
        holder.binding.pdfDownload.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var pos =  holder.getAdapterPosition()
                 var url= items[pos].fileUrl
                val request = DownloadManager.Request(Uri.parse(url))

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded_pdf.pdf")

                val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }
        })
    }


    class ViewHolder(
        val binding: LayoutPdfBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(name: String) {
            binding.pdfName.text = name
        }
    }
}