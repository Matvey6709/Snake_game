package com.example.share2dlibgdx;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import datamanager.Player;

public class FireBaseDataBase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    Player players;
    ArrayList<String> names;
    ArrayList<String> cords;
    ArrayList<String> levels;
    ArrayList<Integer> appleX;
    ArrayList<Integer> appleY;

    String nGame;
    int cPg = -1;
    String nameFirstPlayer;
    String gg;

    public void Create() {
        //        firebaseDatabase = FirebaseDatabase.getInstance("https://share-3c976-default-rtdb.firebaseio.com/");
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        players = new Player();
        names = new ArrayList<>();
        cords = new ArrayList<>();
        levels = new ArrayList<>();
        appleX = new ArrayList<>();
        appleY = new ArrayList<>();
    }

    public Player requestData(String nameGame, String namePlayer, Snake share) {
        ref = firebaseDatabase.getReference("Games").child(nameGame);
        ValueEventListener v = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Player player = new Player();

                Map<String, Object> val = (Map<String, Object>) snapshot.getValue();

                for (Map.Entry<String, Object> entry : val.entrySet()) {
                    String value = (String) entry.getKey();
                    String cord = (String) snapshot.child(value).child("cords").getValue();
                    String level = (String) snapshot.child(value).child("level").getValue();
//                    int apX = (int) snapshot.child(value).child("appleX").getValue().hashCode();
//                    int apY = (int) snapshot.child(value).child("appleY").getValue().hashCode();
                    names.add(value);
                    cords.add(cord);
                    levels.add(level);
//                    appleX.add(apX);
//                    appleY.add(apY);
                }

                if (names.get(0).equals(share.NamePlayer)) {
                    player.setStr(snapshot.child(names.get(1)).child("cords").getValue().toString());
                    player.setLevel(Integer.parseInt(levels.get(1)));
                    player.setName(names.get(1));
//                    player.setApplesX(appleX.get(1));
//                    player.setApplesX(appleY.get(1));
                } else {
                    player.setStr(snapshot.child(names.get(0)).child("cords").getValue().toString());
                    player.setLevel(Integer.parseInt(levels.get(0)));
                    player.setName(names.get(0));
//                    player.setApplesX(appleX.get(0));
//                    player.setApplesX(appleY.get(0));
                }


                assert player != null;
                players = player;

                names.clear();
                cords.clear();
                levels.clear();
                appleX.clear();
                appleY.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка в базе данных");
            }
        };
        ref.addValueEventListener(v);
        if (players != null) {
            return players;
        }
        return null;
    }


    public void putData(String nameGame, String namePlayer, String vector2, String level) {
        ref = firebaseDatabase.getReference();
        ref.child("Games").child(nameGame).child(namePlayer).child("cords").setValue(vector2);
        ref.child("Games").child(nameGame).child(namePlayer).child("level").setValue(level);
//        ref.child("Games").child(nameGame).child(namePlayer).child("appleX").setValue(x);
//        ref.child("Games").child(nameGame).child(namePlayer).child("appleY").setValue(y);
    }

    public String checkStartPlay() {
        ref = firebaseDatabase.getReference("Games");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> val = (Map<String, Object>) snapshot.getValue();
                for (Map.Entry<String, Object> entry : val.entrySet()) {
                    String nameGame = (String) entry.getKey();
                    if (snapshot.child(nameGame).getChildrenCount() != 2) {
                        assert nameGame != null;
                        nGame = nameGame;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return nGame;
    }

    public int countPlayersGames(String nameGame) {
        try {
            ref = firebaseDatabase.getReference("Games").child(nameGame);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String num = snapshot.getChildrenCount() + "";
                    assert num != null;
                    cPg = Integer.parseInt(num);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }

        return cPg;
    }

    public String getFirstPlayer(String nameGame) {
//        player.clear();
//        ref = firebaseDatabase.getReference("Games").child(nameGame);
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                String f = snapshot.getValue().toString();
//                f = f.substring(1, f.indexOf("="));
//                player.add(f);
//                gg = player.get(0);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        if (player.size() > 0) {
//            System.out.println(player.get(0));
//            nameFirstPlayer = player.get(0);
//        }
//        System.out.println(gg);
////        assert nameFirstPlayer != null;
//        nameFirstPlayer = gg;
        return null;
    }

}
