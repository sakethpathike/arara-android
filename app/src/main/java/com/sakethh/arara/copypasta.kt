package com.sakethh.arara

/*class DownloadsImage extends AsyncTask<String, Void,Void>{

    @Override
    protected Void doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bm = null;
        try {
            bm =    BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/AndroidDvlpr"); //Creates app specific folder

        if(!path.exists()) {
            path.mkdirs();
        }

        File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();
            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(MainActivity.this,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    // Log.i("ExternalStorage", "Scanned " + path + ":");
                    //    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
        } catch(Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        showToast("Image Saved!");
    }
}*/
