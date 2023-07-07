package com.stockmanagementsystem.managestock.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.activity.AddMedicineActivity;
import com.stockmanagementsystem.managestock.adapters.MedicineAdapter;
import com.stockmanagementsystem.managestock.adapters.MedicineQuantityAdapter;
import com.stockmanagementsystem.managestock.interfaces.ItemClickListener2;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;
import com.stockmanagementsystem.managestock.modelCLasses.CreateBill;
import com.stockmanagementsystem.managestock.modelCLasses.GetMedicineData;
import com.stockmanagementsystem.managestock.modelCLasses.MedicineAndQuantity;
import com.stockmanagementsystem.managestock.modelCLasses.SaveUserInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFragment extends Fragment {

    EditText name, age, email, phone, quantity, price, drName;
    //    TextView manufactureDate, expireDate;
    TextView medicine;
    Button save;
    private ArrayList<AddMedicineModelView> getMedicineList = new ArrayList<>();
    private ArrayList<AddMedicineModelView> getSelectedMedicineList = new ArrayList<>();
    private ArrayList<MedicineAndQuantity> selectedMedicineNameList = new ArrayList<>();
    ArrayList<String> arrayList;
    ArrayList<Double> arrayListMedicinePrice;
    ArrayList<Integer> arrayListQuantity;
    ArrayList<String> listFinalQuantity = new ArrayList<>();
    ArrayList<String> ListFinalSelectedMedicine = new ArrayList<>();

    Dialog dialog;
    private RecyclerView rvSelectedMedicine;
    String uid = FirebaseAuth.getInstance().getUid();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    double totalMedicinePrice = 0;
    public MedicineAdapter medicineAdapter;
    ItemClickListener2 itemClickListener;
    String finalQuantity = "-1";


    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        name = view.findViewById(R.id.username);
        age = view.findViewById(R.id.userAge);
        email = view.findViewById(R.id.userEmail);
        phone = view.findViewById(R.id.userPhone);
        medicine = view.findViewById(R.id.enterMedicine);
//        manufactureDate = view.findViewById(R.id.enterManufactureDate);
//        expireDate = view.findViewById(R.id.enterExpireDate);
        quantity = view.findViewById(R.id.enterQuantity);
        price = view.findViewById(R.id.enterPrice);
        drName = view.findViewById(R.id.enterDrName);
        save = view.findViewById(R.id.btnSave);
        save = view.findViewById(R.id.btnSave);
        rvSelectedMedicine = view.findViewById(R.id.rvSelectedMedicine);
        arrayList = new ArrayList<>();
        arrayListMedicinePrice = new ArrayList<>();
        arrayListQuantity = new ArrayList<>();

        getMedicineData();


//        manufactureDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//                long currentTime = Calendar.getInstance().getTimeInMillis();
//                // Create a new DatePickerDialog and show it
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                manufactureDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                datePickerDialog.getDatePicker().setMaxDate(currentTime - 24 * 60 * 60 * 1000); // Set minimum date to yesterday
//                datePickerDialog.show();
//            }
//        });
//
//        expireDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Calendar c = Calendar.getInstance();
//                int year = c.get(Calendar.YEAR);
//                int month = c.get(Calendar.MONTH);
//                int day = c.get(Calendar.DAY_OF_MONTH);
//
//                long currentTime = Calendar.getInstance().getTimeInMillis();
//
//                // Create a new DatePickerDialog and show it
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                expireDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                            }
//                        }, year, month, day);
//                datePickerDialog.getDatePicker().setMinDate(currentTime + 365 * 24 * 60 * 60 * 1000); // Set maximum date to one year from today
//                datePickerDialog.show();
//            }
//        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean info = true;

                if (name.getText().toString().equals("")) {
                    name.setError("invalid");
                    info = false;
                }
                if (age.getText().toString().equals("")) {
                    age.setError("invalid");
                    info = false;
                }
                if (email.getText().toString().equals("")) {
                    email.setError("invalid");
                    info = false;
                }
                if (!isGmailFormat(email.getText().toString())) {
                    email.setError("invalid");
                    info = false;
                }
                if (phone.getText().toString().equals("")) {
                    phone.setError("invalid");
                    info = false;
                }
                if (medicine.getText().toString().equals("")) {
                    medicine.setError("invalid");
                    info = false;
                }
//                if (manufactureDate.getText().toString().equals("")) {
//                    manufactureDate.setError("invalid");
//                    info = false;
//                }
//                if (expireDate.getText().toString().equals("")) {
//                    expireDate.setError("invalid");
//                    info = false;
//                }

                if (price.getText().toString().equals("")) {
                    price.setError("invalid");
                    info = false;
                }
                if (drName.getText().toString().equals("")) {
                    drName.setError("invalid");
                    info = false;
                }
                if (info) {
                    addNewBillDataToFireStore();
                }
            }
        });

        listFinalQuantity.add("-1");
        listFinalQuantity.add("-1");
        listFinalQuantity.add("-1");
        listFinalQuantity.add("-1");
        listFinalQuantity.add("-1");
        itemClickListener = new ItemClickListener2() {
            @Override
            public void onClick(int position, String quantity, String price2,ArrayList<String> names) {
                finalQuantity = quantity;
                listFinalQuantity.add(position, quantity);
                price.setText(price2);
                ListFinalSelectedMedicine = names;
            }
        };
        searchableSpinner();
        return view;
    }

    public static boolean isGmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:gmail|googlemail)\\." +
                "(?:com)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static String getTodayDate() {
        LocalDate today = null;
        String formattedDate = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedDate = today.format(formatter);
        }
        return formattedDate;
    }


    private void addNewBillDataToFireStore() {
        CreateBill bill = new CreateBill();
        bill.uploadData(name.getText().toString(), age.getText().toString(), email.getText().toString(), phone.getText().toString(),
                ListFinalSelectedMedicine,  price.getText().toString(), drName.getText().toString(), getTodayDate());


        for (int i = 0; i < getSelectedMedicineList.size(); i++) {
            updateData(i, getSelectedMedicineList.get(i).getMedicine(), getSelectedMedicineList.get(i).getQuantity());
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(AddFragment.this.getId(), new AddFragment()).commit();

    }


    public void searchableSpinner() {

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(900, 1300);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        getSelectedMedicineList.add(getMedicineList.get(position));
                        rvSelectedMedicine.setLayoutManager(new LinearLayoutManager(getActivity()));
                        MedicineQuantityAdapter adapter = new MedicineQuantityAdapter(getSelectedMedicineList, itemClickListener);
                        rvSelectedMedicine.setAdapter(adapter);
//                            totalMedicinePrice = totalMedicinePrice +Integer.parseInt( getMedicineList.get(position).getPrice());
//                        price.setText(String.valueOf(totalMedicinePrice));
                    }
                });
            }
        });

    }


    private void getMedicineData() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference uidRef = database.getReference("AllMedicineList").child(uid);

        uidRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    AddMedicineModelView medicine = snapshot.getValue(AddMedicineModelView.class);

                    Log.d("Medicine", medicine.toString());

                    getMedicineList.add(medicine);

                    String medicineName = medicine.getMedicine();
                    String company = medicine.getCompany();
                    String manufactureDate = medicine.getManufactureDate();
                    String expireDate = medicine.getExpireDate();
                    String quantity = medicine.getQuantity();
                    String price = medicine.getPrice();
                    arrayList.add(medicineName + " * " + quantity);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void updateData(int position, String medicine, String quantity) {
        if (!listFinalQuantity.get(position).isEmpty()) {
            quantity = String.valueOf(Integer.parseInt(quantity) - Integer.parseInt(listFinalQuantity.get(position)));


            if (quantity.equals("0")) {
                FirebaseDatabase.getInstance().getReference("AllMedicineList").child(uid).child(medicine).removeValue();
                return;
            }


            HashMap<String, Object> mediData = new HashMap<>();

            mediData.put("quantity", quantity);


            FirebaseDatabase.getInstance().getReference("AllMedicineList").child(uid).child(medicine).updateChildren(mediData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    assert getFragmentManager() != null;
                    FragmentTransaction f = getFragmentManager().beginTransaction();
                    f.replace(R.id.fragment_container, new HomeFragment());
                    f.addToBackStack(null);
                    f.commit();
                }
            });
        }
    }


}


