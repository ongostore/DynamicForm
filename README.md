# DynamicForm

//Step 1

implements DynamicServiceForm.DynamicServiceFormListener

//Step 2

 @Override
 
public void onSuccess(String status) {

if (status.equalsIgnoreCase("1")) {

Toast.makeText(this, "Success Library.", Toast.LENGTH_SHORT).show();

}

}


//step 3

DynamicServiceForm dynamicServiceForm = new DynamicServiceForm(this, R.id.container, "http://xx.1x.1x.xxx:8081", "xx", "xxx@x.x", this);

dynamicServiceForm.getForm("Posts", null);

//Dependency

 implementation 'com.github.ongostore:DynamicForm:1.0.0' (which ever is latest)

//Add in project level Build Gradle

allprojects {

repositories {

...

maven { url 'https://jitpack.io' }

}

}
