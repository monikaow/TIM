package pl.edu.wat.spz.fragments.visit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pl.edu.wat.spz.utils.Action;
import pl.edu.wat.spz.R;
import pl.edu.wat.spz.activities.ChangeVisitActivity;
import pl.edu.wat.spz.entity.MedicalVisit;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class VisitRecyclerViewAdapter extends RecyclerView.Adapter<VisitRecyclerViewAdapter.VisitViewHolder> {

    private final List<MedicalVisit> list;
    private final Context context;

    public VisitRecyclerViewAdapter(List<MedicalVisit> items, Context context) {
        this.list = items;
        this.context = context;
    }

    @NonNull
    @Override
    public VisitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_visits, parent, false); //pojedynczy wyglad
        return new VisitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VisitViewHolder viewHolder, int position) {
        final MedicalVisit item = list.get(position);
        viewHolder.addItem(item, new Action<MedicalVisit>() {
            @Override
            public void action(MedicalVisit medicalVisit) {
                list.remove(medicalVisit);
                notifyDataSetChanged();
            }
        });
        viewHolder.doctorName.setText(item.getDoctorSpecialization().getDoctor().getFullTitle());
        viewHolder.specialization.setText(item.getDoctorSpecialization().getSpecialization().getName());
        viewHolder.visitDate.setText(item.getDoctorTimetable().getDate());

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(viewHolder.item);
            }
        });
    }

    private void openDialog(MedicalVisit item) { //szzegoly wizyty
        new AlertDialog.Builder(this.context)
                .setTitle("Szczegóły wizyty")
                .setMessage(item.getDetails())
                .setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VisitViewHolder extends RecyclerView.ViewHolder { //zarzadzanie
        private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        final View view;
        final TextView doctorName;
        final TextView specialization;
        final TextView visitDate;
        final RelativeLayout actionButtons;
        MedicalVisit item;

        VisitViewHolder(View view) {
            super(view);
            this.view = view;
            this.doctorName = view.findViewById(R.id.doctorName);
            this.specialization = view.findViewById(R.id.specialization);
            this.visitDate = view.findViewById(R.id.visitDate);
            this.actionButtons = view.findViewById(R.id.actionButtons);
        }

        public void addItem(final MedicalVisit item, final Action onCancelListener) {
            this.item = item;

            try {
                if (simpleDateFormat.parse(item.getDoctorTimetable().getDate()).after(new Date())) {
                    actionButtons.setVisibility(View.VISIBLE);

                    view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences prefs = v.getContext().getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
                            final String authorizationHeader = prefs.getString(LoginPreferences.AUTHORIZATION_HEADER_KEY, null);

                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Czy chcesz anulować wizytę?")
                                    .setMessage("Tej operacji nie da się cofnąć")
                                    .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            Call<MedicalVisit> medicalVisitCall = HttpClient.getInstance().getVisitService().cancelVisit(authorizationHeader, item.getId());
                                            medicalVisitCall.enqueue(new Callback<MedicalVisit>() {
                                                @Override
                                                public void onResponse(Call<MedicalVisit> call, Response<MedicalVisit> response) {
                                                    onCancelListener.action(item);
                                                }

                                                @Override
                                                public void onFailure(Call<MedicalVisit> call, Throwable t) {

                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {}
                                    })
                                    .create().show();
                        }
                    });

                    view.findViewById(R.id.changeButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {//zmiana daty
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ChangeVisitActivity.class);
                            intent.putExtra("MEDICAL_VISIT", item);
                            context.startActivity(intent);

                        }
                    });
                }
                else {
                    actionButtons.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
