package com.example.noleftovers.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public class Food {
        String name;
        int amount;
        Date addedDate;
        Date dateExpired;
        Date daysLeft;
        List<String> categories;

        public Food(String name, int amount, List<String> categories, Date dateExpired) {
            this.name = name;
            this.amount = amount;
            this.categories = categories;
            this.addedDate = getToday();
            this.dateExpired = dateExpired;
        }

        private Date getToday() {
            Calendar calendar = Calendar.getInstance();
            return calendar.getTime();
        }

        /* Return the number of days remaining before expiration. */
        public int getDaysLeft() {
            long durationL = dateExpired.getTime()-getToday().getTime();
            int duration = (int) ((durationL / (1000 * 60 * 60 * 24)));
            return duration;
        }

        public void updateAmount(int change) {
            amount += change;
        }
    }
}