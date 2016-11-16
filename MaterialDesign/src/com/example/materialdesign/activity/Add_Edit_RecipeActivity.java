package com.example.materialdesign.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;

public class Add_Edit_RecipeActivity extends AppCompatActivity{
	ImageView ivPictureChoose;
	Button btnSave;
	EditText etTitle;
	EditText etIngredient;
	EditText etPreparation;
	private Toolbar mToolbar;
	private static final int CAMERA_CAPTURE = 20;
	private static int RESULT_LOAD_IMG = 1;
	String imgDecodableString;
	String timeStamp;
	String imageFileName;
    Boolean isEditing=false;
    int recipeId;
    Bundle bundle;
    LayoutInflater inflater;
	View customToastroot;
	TextView txtView;
	Toast customtoast;
	MyCustomRecipe my=new MyCustomRecipe();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_recipe);
		bundle=savedInstanceState;
		btnSave=(Button)findViewById(R.id.btnsave);
        ivPictureChoose = (ImageView)findViewById(R.id.ivPictureChoose);
        etTitle=(EditText)findViewById(R.id.title);
        etIngredient=(EditText)findViewById(R.id.ingredient);
        etPreparation=(EditText)findViewById(R.id.preparation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        inflater=getLayoutInflater();
        customtoast=new Toast(getApplicationContext());
        customToastroot=inflater.inflate(R.layout.mycustom_toast, null);
        txtView=(TextView) customToastroot.findViewById(R.id.toastTextView);
        customtoast.setView(customToastroot);
		customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,-300);
		customtoast.setDuration(Toast.LENGTH_SHORT);
        Bundle extra=getIntent().getExtras();
        if(extra==null){
        }
        else{
        	isEditing=true;
        	recipeId=extra.getInt("recipeId");
        	etTitle.setText(extra.getString("recipeTitle"));
        	etTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
        	try{
		    	 ivPictureChoose.setImageBitmap(BitmapFactory.decodeFile(extra.getString("recipeImage")));
					    	 
		     }catch(Exception e){
		    	 
		     }
        	etIngredient.setText(extra.getString("recipeIngredient"));
        	etIngredient.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
        	etPreparation.setText(extra.getString("recipePreparation"));
        	etPreparation.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etTitle.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) { 				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int start, int before,
					int count) {
				if(arg0.length()==0){
					etTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				}
				else{
					etTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
				}
			}	
		});
        etIngredient.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {			
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int start, int before,int count) {
				
				if(arg0.length()==0){
					etIngredient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				}
				else{
					etIngredient.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
				}
			}	
		});
		etPreparation.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int start, int before,int count) {

				if(arg0.length()==0){
					etPreparation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
				}
				else{
					etPreparation.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
				}
			}	
		});
        ivPictureChoose.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				final Dialog dialog = new Dialog(Add_Edit_RecipeActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.choose_picture);
        		dialog.show();
        		ImageView camera=(ImageView)dialog.findViewById(R.id.camera);
        		ImageView gallery=(ImageView)dialog.findViewById(R.id.gallery);
				camera.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
					     Shootpicture();
					     
					     dialog.dismiss();
					}
				});
				gallery.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						loadImagefromGallery(arg0);
						dialog.dismiss();
						
					}
				});
        		//Intent intent = new Intent(getApplicationContext(),ImgTest.class);
				//startActivityForResult(intent,request_code);
			}
        });
        btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String title=etTitle.getText().toString();
				String ingredient=etIngredient.getText().toString();
				String preparation=etPreparation.getText().toString();
				DatabaseHandler db = new DatabaseHandler(getApplicationContext());
				List<MyCustomRecipe> myrecipe = db.getAllMyRecipe();
		        if(title.equals("")){
		        	txtView.setText("Recipe name cannot be blank!");
	    			customtoast.show();
		        	return;
		        }
				if(!isEditing)
					for (MyCustomRecipe rec : myrecipe) {
			        	//Toast.makeText(getApplicationContext(),rec.getID()+"\n"+rec.getName()+"\n"+rec.getImage()+"\n"+rec.getIngredient()+"\n"+rec.getPreparation(),2000).show();
			            if(rec.getName().equals(title)){
			            	txtView.setText("There is a recipe with the same name.Choose another name.");
			    			customtoast.show();
			            	return;
			            }  
			        }
				
				my.setName(title);
				SaveImageToInternalStorage(arg0);
				my.setImage(getExternalFilesDir("")+"/"+imageFileName+".jpg");
				my.setIngredient(ingredient);
				my.setPreparation(preparation);
				if(isEditing)
					db.updateMy(my,recipeId);
				else 
					db.addMy(my);
				db.close();
				finish();	
                //List<MyCustomRecipe> myrecipe = db.getAllMyRecipe();
		        //for (MyCustomRecipe rec : myrecipe) {
		        //	Toast.makeText(getApplicationContext(),rec.getID()+"\n"+rec.getName()+"\n"+rec.getImage()+"\n"+rec.getIngredient()+"\n"+rec.getPreparation(),2000).show();
		        //}
			}
		});
	}
	public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
          case android.R.id.home:
          Log.e("onOptionsItemSelected", "home");
              finish();
          return false;
       }


        return super.onOptionsItemSelected(item);
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
	    	if(requestCode == CAMERA_CAPTURE && resultCode == RESULT_OK)
			{
				
				//Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir("")+"/"+imageFileName+".jpg");
				 // set the custom dialog components - text, image and button		     
			     try{
			    	 ivPictureChoose.setImageBitmap(BitmapFactory.decodeFile(getExternalFilesDir("")+"/"+imageFileName+".jpg"));
						    	 
			     }catch(Exception e){
			    	 
			     }
			}
			// When an Image is picked
			else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
					&& null != data) {
				// Get the Image from data

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				imgDecodableString = cursor.getString(columnIndex);
				cursor.close();
				// Set the Image in ImageView after decoding the String
				ivPictureChoose.setImageBitmap(BitmapFactory
						.decodeFile(imgDecodableString));
				
				
				
			} else {
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
    			
    }
	public void Shootpicture(){
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);		
		 if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			 
			 File photoFile = null;
				try {
					 photoFile = CreateImageFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(photoFile != null)
				{
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
					startActivityForResult(cameraIntent, CAMERA_CAPTURE);									
				}		        
		    }		
	}
	public void loadImagefromGallery(View view) {
		// Create intent to Open Image applications like Gallery, Google Photos
		Intent galleryIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// Start the Intent
		startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
	}
	
	public void SaveImageToInternalStorage(View view) {
		try {
		Bitmap bmp=BitmapFactory
				.decodeFile(imgDecodableString);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		FileOutputStream outStream = null;
		    timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		    imageFileName = "Image_" + timeStamp + "_";
			outStream = new FileOutputStream(getExternalFilesDir("")+"/"+imageFileName+".jpg");
			outStream.write(byteArray);
			outStream.close();
			txtView.setText("Picture Saved");
			customtoast.show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(IllegalStateException e)
		{
			Toast.makeText(getApplicationContext(), "Choose a image first!", 2000).show();
		}catch(NullPointerException e)
		{
		}
		finally {
		}
	}
	private File CreateImageFile() throws IOException
	{
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		imageFileName = "Image_" + timeStamp;
		Log.i("SAVE IMG : ", imageFileName);
		File storageDirectory = new File(getExternalFilesDir("").toString());	
		if(storageDirectory.exists())
			storageDirectory.mkdirs();
		
		File image = new File(storageDirectory, imageFileName+".jpg");
		return image;
		
	}
}
