package com.example.share2dlibgdx;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

import datamanager.Player;

public class FireBaseDataBase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref, ref2;
    Player players;

    ValueEventListener v;
    String nGame;
    int cPg = -1;
    boolean isOnline = false, isExistsGame = true;
    String uniqueKey;


    public void Create() {
        firebaseDatabase = FirebaseDatabase.getInstance();

        players = new Player();

        ref = firebaseDatabase.getReference("Games");

        uniqueKey = new Random().nextInt(90000) + 10000 + "" + System.currentTimeMillis();
    }

    public Player requestData(String nameGame) {
        ref2 = firebaseDatabase.getReference("Games").child(nameGame);
        v = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Player player;
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        player = dataSnapshot.getValue(Player.class);
                        if ((uniqueKey).equals(dataSnapshot.getKey())) {
                            continue;
                        }
                        players = player;
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка в базе данных");
            }

        };
        ref2.addValueEventListener(v);
        if (players != null) {
            return players;
        }
        return null;
    }

    public Player requestData2() {
        return players;
    }

    public void putData(String nameGame, String vector2) {
        ref.child(nameGame).child(uniqueKey).child("cords").setValue(vector2);
    }

    public void putData(String nameGame, String namePlayer, String level, int x, int y) {
        ref.child(nameGame).child(uniqueKey).child("level").setValue(level);
        ref.child(nameGame).child(uniqueKey).child("appleX").setValue(x);
        ref.child(nameGame).child(uniqueKey).child("appleY").setValue(y);
        ref.child(nameGame).child(uniqueKey).child("name").setValue(namePlayer);
    }

    public String checkStartPlay() {

        v = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nGame = null;
                Map<String, Object> val = (Map<String, Object>) snapshot.getValue();
                for (Map.Entry<String, Object> entry : val.entrySet()) {
                    String nameGame = (String) entry.getKey();
                    if (snapshot.child(nameGame).getChildrenCount() != 2) {
                        assert nameGame != null;
                        if (nameGame != null) {
                            nGame += nameGame + " ";
                        }

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(v);
        return nGame;
    }

    public String checkStartPlay2() {
        return nGame;
    }

    public int countPlayersGames(String nameGame) {
        try {
            v = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String num = snapshot.getChildrenCount() + "";
                    assert num != null;
                    cPg = Integer.parseInt(num);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            ref.child(nameGame).addValueEventListener(v);
        } catch (Exception e) {

        }
        return cPg;
    }

    public int countPlayersGames2() {
        return cPg;
    }

    public void dispose() {
        try {
            ref.removeEventListener(v);
        } catch (Exception e) {

        }

    }

    public void dispose2() {
        try {
            ref2.removeEventListener(v);
        } catch (Exception e) {

        }

    }

    public boolean isOnline() {
        return isOnline;
    }

    public void isOnline2(String nameGame) {
        DatabaseReference presenceRef = FirebaseDatabase.getInstance().getReference("disconnectmessage");
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");

        v = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                    isOnline = true;
                } else {
                    System.out.println("not connected");
                    isOnline = false;
                }

                ref.child(nameGame).onDisconnect().setValue(null);
                presenceRef.onDisconnect().setValue("I disconnected!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        };
        connectedRef.addValueEventListener(v);
    }

    public void delete(String nameGame) {
        ref.child(nameGame).setValue(null);

    }

    public void setExistsGame(boolean existsGame) {
        isExistsGame = existsGame;
    }

    public void isExistsGame(String nGame) {
        v = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isExistsGame = snapshot.child(nGame).exists();
                if (snapshot.child(nGame).getChildrenCount() < 2) {
                    isExistsGame = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(v);
    }

    public boolean isExistsGame2() {
        return isExistsGame;
    }
}
