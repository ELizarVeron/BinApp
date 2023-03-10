package com.android.binapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.binapp.data.retrofit.BinResponse
import com.android.binapp.data.retrofit.RetrofitServices
import com.android.binapp.data.storage.BinDAO
import com.android.binapp.domain.models.BinInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class Repository(private val binDAO: BinDAO, private val rService: RetrofitServices) {
    val TAG = "Repository"
   fun loadRecordsFromDB(): Flow<List<BinInfoEntity>>{
       return binDAO.getAll()
   }

  fun insertRecord(bin:Int , binInfoEntity: BinInfoEntity){
       binInfoEntity.number = bin
       binDAO.insertInfo(binInfoEntity)
   }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch{
            binDAO.deleteAll()
        }

    }

    fun delete(id:Int){
        CoroutineScope(Dispatchers.IO).launch{
            binDAO.delete(id)
        }

    }


    fun getRecordFromApi(bin:Int, mutableLiveData: MutableLiveData<Boolean>  ){
        rService.getBinInfo(bin).enqueue(object :
            retrofit2.Callback<BinResponse>{
            override fun onResponse(call: Call<BinResponse>, response: Response<BinResponse>) {
                response.body()?.let {
                    CoroutineScope(Dispatchers.IO).launch{
                        insertRecord( bin, it.toBinInfoEntity() )
                    }
                }
                if(response.body()==null){
                    mutableLiveData.value = true
                }



            }

            override fun onFailure(call: Call<BinResponse>, t: Throwable) {
                Log.e(TAG,t.message.toString())
            }


        })
   }

   fun BinResponse.toBinInfoEntity():BinInfoEntity{
       return BinInfoEntity(0,
           this.number?.length?:0,
           this.number?.luhn?:false,
           this.scheme?:"?????? ????????????",
           this.type?:"?????? ????????????",
           this.brand?:"?????? ????????????",
           this.prepaid?:false,
           this.country?.numeric?:"?????? ????????????",
           this.country?.alpha2?:"?????? ????????????",
           this.country?.name?:"?????? ????????????",
           this.country?.emoji?:"?????? ????????????",
           this.country?.currency?:"?????? ????????????",
           this.country?.latitude?:0,
           this.country?.longitude?:0,
           this.bank?.name?:"?????? ????????????",
           this.bank?.url?:"?????? ????????????",
           this.bank?.phone?:"?????? ????????????",
           this.bank?.city?:"?????? ????????????",
       )
   }



}