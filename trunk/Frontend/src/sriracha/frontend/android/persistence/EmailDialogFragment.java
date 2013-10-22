package sriracha.frontend.android.persistence;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import sriracha.frontend.*;
import sriracha.frontend.R;
import android.content.Intent;
import android.net.Uri;
import java.io.*;

import java.io.*;

/**
 * The dialog box for loading a circuit from the filesystem.
 * Must be called from MainActivity, since the callback that talks to the storage is located there.
 */
public class EmailDialogFragment extends DialogFragment
{
    public static EmailDialogFragment newInstance()
    {
        return new EmailDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().setTitle("Email Circuit");
        View view = inflater.inflate(R.layout.email_file_dialog, container, false);

        try
        {
            String[] files = new Storage(getActivity()).list(".occ");
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, files);

            ListView filesList = (ListView) view.findViewById(R.id.load_file_list);
            filesList.setAdapter(adapter);
            filesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                {
                    //Send email

                    //Fetch email and subject from form
                    String email = ((TextView)getDialog().findViewById(R.id.recipient_email)).getText().toString();
                    String subject =  ((TextView)getDialog().findViewById(R.id.subject)).getText().toString();
                  //  String body = ((TextView)getDialog().findViewById(R.id.body)).getText().toString();

                    if (email.isEmpty())
                    {
                        ((MainActivity)getActivity()).showToast("Please enter an email");
                        return;
                    }
                    //Fetch filename
                    String fileName = (adapter.getItem(position));

                    System.out.println("fileName is:"+fileName);
                    System.out.println("Email:"+email);
                    System.out.println("Subject :"+subject);

                    //Set body
                    //String body = "Attached is a circuit, please download the file at the specified location to be able to use it in the application";


                    String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();

                    System.out.println("===================");
                    System.out.println("===================");

                    System.out.println("===================");
                    System.out.println(baseDir);
                    System.out.println("===================");
                    System.out.println("===================");
                    System.out.println("===================");
                    //Actual sending code
                    Intent sendIntent;
                    sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                //    sendIntent.putExtra(Intent.EXTRA_TEXT, body);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" +"/mnt/sdcard/Android/data/sriracha.frontend/files/"+ fileName));

                    // intent.setData(Uri.parse("mailto:"));
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    try {
                        startActivity(Intent.createChooser(sendIntent, "Send Circuit"+ fileName));
                        dismiss();
                    } catch (android.content.ActivityNotFoundException ex) {
                       // Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    //loadCircuit(adapter.getItem(position));
                }
            });

            view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dismiss();
                }
            });
        }
        catch (IOException e)
        {
            Toast toast = Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            dismiss();
        }

        return view;
    }

    private void loadCircuit(String fileName)
    {
        if (((MainActivity) getActivity()).loadCircuit(fileName))
            dismiss();
    }

    public String[] explode(String s) {
    String[] arr = new String[s.length()];
    for(int i = 0; i < s.length(); i++)
    {
        arr[i] = String.valueOf(s.charAt(i));
    }
    return arr;
    }
}
