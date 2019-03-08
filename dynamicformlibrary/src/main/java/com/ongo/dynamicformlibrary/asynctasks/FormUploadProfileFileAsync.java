package com.ongo.dynamicformlibrary.asynctasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import com.ongo.dynamicformlibrary.utils.FormConstants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class FormUploadProfileFileAsync extends AsyncTask<Void, Void, Void> {

		private HttpResponse response;
		private String imagePathsHashMap;
		private Context mContext;
		private ProgressDialog mProgressDialog;
		private boolean isDialogCancelled = false;
		private ProfileImageUploaded imageUploaded;
		private String responseString;
		private String serverFileName;
		private SharedPreferences.Editor mPrefEditor;
		private SharedPreferences mPreferences;
	 

		public interface ProfileImageUploaded {
			void isProfileFileUploaded(String imagUrl);
		}
		
		public FormUploadProfileFileAsync(ProfileImageUploaded profileImageUploaded, String imagePaths, String serverFileName, Context mContext) {
			this.mContext = mContext;
			this.imagePathsHashMap = imagePaths;
			this.serverFileName=serverFileName;
			imageUploaded=profileImageUploaded;
			 
		}
		
		@SuppressLint("InlinedApi")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
				mProgressDialog = new ProgressDialog(mContext);
				mProgressDialog.setCancelable(false);
				mProgressDialog.setMessage("Loading..");
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mProgressDialog.setOnCancelListener(new OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						isDialogCancelled = true;
						dialog.dismiss();
					}
				});
				mProgressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
					uploadPic();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private void uploadPic() {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpPost httpPost = new HttpPost(FormConstants.uploadFile());
				FileBody fileBody = new FileBody(new File(imagePathsHashMap));
				
				MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("srcFile", fileBody);
				reqEntity.addPart("refFileName", new StringBody(serverFileName));
				
				httpPost.setEntity(reqEntity);              
				response = httpClient.execute(httpPost, localContext);
				changeHashMap(EntityUtils.toString(response.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void changeHashMap(String jsonRes) {
			JSONObject outerJson = null;
			String status= null;
			 
			try {
				Log.e(FormUploadProfileFileAsync.class.getSimpleName(), "JSON" + jsonRes);
				outerJson = new JSONObject(jsonRes);
				if (outerJson.has("status")) {
					status = outerJson.getString("status");
				}
				
				if (outerJson.has("filePath")) {
					responseString = outerJson.getString("filePath");
				}
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (!isDialogCancelled) {
				mProgressDialog.dismiss();
				try {
				imageUploaded.isProfileFileUploaded(responseString);
					mPreferences = mContext.getSharedPreferences(FormConstants.PREF_NAME, MODE_PRIVATE);
					mPrefEditor = mPreferences.edit();
					mPrefEditor.putString(FormConstants.PREF_PROFILE_IMAGE, responseString);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


	}
