package com.example.share2dlibgdx;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import datamanager.Player;

public class FireBaseDataBase {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    Player players;

    ValueEventListener v;
    String nGame;
    int cPg = -1;

    public void Create() {
        //        firebaseDatabase = FirebaseDatabase.getInstance("https://share-3c976-default-rtdb.firebaseio.com/");
        firebaseDatabase = FirebaseDatabase.getInstance();

        players = new Player();

        ref = firebaseDatabase.getReference("Games");

    }

    public Player requestData(String nameGame, String namePlayer, Snake share) {
        ref2 = firebaseDatabase.getReference("Games").child(nameGame);
        v = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                names = new Array<>();
//                cords = new Array<>();
//                levels = new Array<>();
//                appleX = new Array<>();
//                appleY = new Array<>();
//
//                Player player = new Player();
//
//                Map<String, Object> val = (Map<String, Object>) snapshot.getValue();
//
//                for (Map.Entry<String, Object> entry : val.entrySet()) {
//                    String value = entry.getKey();
//                    String cord = (String) snapshot.child(value).child("cords").getValue();
//                    String level = (String) snapshot.child(value).child("level").getValue();
//                    int apX =  snapshot.child(value).child("appleX").getValue().hashCode();
//                    int apY =  snapshot.child(value).child("appleY").getValue().hashCode();
//                    names.add(value);
//                    cords.add(cord);
//                    levels.add(level);
//                    appleX.add(apX);
//                    appleY.add(apY);
//                }
//                if (names.get(0).equals(share.NamePlayer)) {
//                    player.setStr(snapshot.child(names.get(1)).child("cords").getValue().toString());
//                    player.setLevel(Integer.parseInt(levels.get(1)));
//                    player.setName(names.get(1));
//                    player.setApplesX(appleX.get(1));
//                    player.setApplesX(appleY.get(1));
//                } else {
//                    player.setStr(snapshot.child(names.get(0)).child("cords").getValue().toString());
//                    player.setLevel(Integer.parseInt(levels.get(0)));
//                    player.setName(names.get(0));
//                    player.setApplesX(appleX.get(0));
//                    player.setApplesX(appleY.get(0));
//                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String namePlayer = dataSnapshot.getKey();
//                    if (!namePlayer.equals(share.NamePlayer)) {
//                        String cords = dataSnapshot.child("cords").getValue().toString();
//                        String level = dataSnapshot.child("level").getValue().toString();
//                        int appleX = dataSnapshot.child("appleX").getValue().hashCode();
//                        int appleY = dataSnapshot.child("appleY").getValue().hashCode();
//
//                        player.setStr(cords);
//                        player.setLevel(Integer.parseInt(level));
//                        player.setName(namePlayer);
//                        player.setApplesX(appleX);
//                        player.setApplesX(appleY);
//                        break;
//                    }
//                }
                Player player;
//                int h = 1;
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String valuePlayer = dataSnapshot.getValue().toString();
//                    valuePlayer = valuePlayer.substring(1, valuePlayer.length()-1);
//                    namePlayer2 = dataSnapshot.getKey();
////                    System.out.println(valuePlayer);
//
//                    if(!share.youFirst && h == 2){
//                        String g[] = valuePlayer.split(", ");
//                        for (int i = 0; i < g.length; i++) {
//                            if(g[i].charAt(1) == 'l'){
//                                String j[] = g[i].split("=");
//                                player.setLevel(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(6) == 'Y'){
//                                String j[] = g[i].split("=");
//                                player.setApplesY(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(6) == 'X'){
//                                String j[] = g[i].split("=");
//                                player.setApplesX(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(1) == 'c'){
//                                String j[] = g[i].split("=");
//                                player.setStr(j[1]);
////                                System.out.println(j[1]);
//                            }
//                        }
//                        player.setName(namePlayer2);
//                    }
//
//                    if(share.youFirst && h == 1){
//                        String g[] = valuePlayer.split(", ");
//                        for (int i = 0; i < g.length; i++) {
//                            if(g[i].charAt(1) == 'l'){
//                                String j[] = g[i].split("=");
//                                player.setLevel(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(6) == 'Y'){
//                                String j[] = g[i].split("=");
//                                player.setApplesY(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(6) == 'X'){
//                                String j[] = g[i].split("=");
//                                player.setApplesX(Integer.parseInt(j[1]));
////                                System.out.println(j[1]);
//                            }
//                            if(g[i].charAt(1) == 'c'){
//                                String j[] = g[i].split("=");
//                                player.setStr(j[1]);
////                                System.out.println(j[1]);
//                            }
//                        }
//                        player.setName(namePlayer2);
//                    }
//                    h++;
////                    search2(valuePlayer, "{");
////                    System.out.println(search(valuePlayer, "appleX"));
////                    System.out.println(word);
////                    if(!share.youFirst && i == 1){
////                        cords = valuePlayer.substring(valuePlayer.indexOf("cords=")+6, valuePlayer.indexOf(", level="));
////                        level = valuePlayer.substring(valuePlayer.indexOf("level=")+6, valuePlayer.indexOf(", appleX="));
////                        appleX = valuePlayer.substring(valuePlayer.indexOf("appleX=")+7, valuePlayer.indexOf(", appleY="));
////                        appleY = valuePlayer.substring(valuePlayer.indexOf("appleY=")+7, valuePlayer.indexOf("}"));
////                        break;
////                    }else if(share.youFirst && i == 2){
////                        cords = valuePlayer.substring(valuePlayer.indexOf("cords=")+6, valuePlayer.indexOf(", level="));
////                        level = valuePlayer.substring(valuePlayer.indexOf("level=")+6, valuePlayer.indexOf(", appleX="));
////                        appleX = valuePlayer.substring(valuePlayer.indexOf("appleX=")+7, valuePlayer.indexOf(", appleY="));
////                        appleY = valuePlayer.substring(valuePlayer.indexOf("appleY=")+7, valuePlayer.indexOf("}"));
////                        break;
////                    }
//                }

//                player.setStr(cords);
//                player.setApplesX(Integer.parseInt(appleX));
//                player.setApplesY(Integer.parseInt(appleY));
//                player.setLevel(Integer.parseInt(level));
//                player.setName(namePlayer2);
//                System.out.println(namePlayer2);
//                System.out.println(cords);
//                System.out.println(level);
//                System.out.println(appleX);
//                System.out.println(appleY);


//                try {
//
//                    if (!share.youFirst) {
//                        namePlayer2 = value.substring(1, value.indexOf("={cords="));
//                        cords = value.substring(value.indexOf("cords=") + 6, value.indexOf(", a"));
//                        appleY = value.substring(value.indexOf("appleY=") + 7, value.indexOf(", appleX"));
//                        appleX = value.substring(value.indexOf("appleX=") + 7, value.indexOf(", level"));
//                        level = value.substring(value.indexOf("level=") + 6, value.indexOf("}, "));
//                    }else {
//                        namePlayer2 = value.substring(value.indexOf("}, ")+3, value.lastIndexOf("={cords="));
//                        cords = value.substring(value.lastIndexOf("cords=") + 6, value.lastIndexOf(", appleY"));
//                        appleY = value.substring(value.lastIndexOf("appleY=") + 7, value.lastIndexOf(", appleX"));
//                        appleX = value.substring(value.lastIndexOf("appleX=") + 7, value.lastIndexOf(", level"));
//                        level = value.substring(value.lastIndexOf("level=") + 6, value.lastIndexOf("}}"));
//                    }
//                }catch (Exception e){
//
//                }
//
//                System.out.println(namePlayer2);
//                System.out.println(cords);
//                System.out.println(appleX);
//                System.out.println(appleY);
//                System.out.println(level);
//
//                String value = snapshot.getValue().toString();
////                System.out.println(value);
//                value = value.replace(",", "");
//
//                if(!share.youFirst){
//                    String value2 = value.substring(value.indexOf("={")+2, value.indexOf("} "));
//                    value2 = value2.replace("cords=", "").replace("appleY=", "").replace("appleX=", "").replace("level=", "");
//                    String v[]= value2.split(" ");
//                    player.setStr(v[0]);
//                    player.setApplesY(Integer.parseInt(v[1]));
//                    player.setApplesX(Integer.parseInt(v[2]));
//                    player.setLevel(Integer.parseInt(v[3]));
////                    System.out.println(value2);
//                }else if(share.youFirst){
//                    String value3 = value.substring(value.lastIndexOf("={")+2, value.indexOf("}}"));
//                    value3 = value3.replace("cords=", "").replace("appleY=", "").replace("appleX=", "").replace("level=", "");
//                    String v[]= value3.split(" ");
//                    player.setStr(v[0]);
//                    player.setApplesY(Integer.parseInt(v[1]));
//                    player.setApplesX(Integer.parseInt(v[2]));
////                    player.setLevel(Integer.parseInt(v[3]));
//                    player.setLevel(Integer.parseInt(v[3]));
//
////                    System.out.println(value3);
//                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    player = dataSnapshot.getValue(Player.class);
                    if (share.NamePlayer.equals(player.getName())) {
                        continue;
                    }
                    players = player;
//                    if(!share.youFirst && i == 2){
//                        player = dataSnapshot.getValue(Player.class);
//                        players = player;
//                        System.out.println(player.getName());
//                        break;
//                    }else if(share.youFirst && i == 1){
//                        player = dataSnapshot.getValue(Player.class);
//                        players = player;
//                        System.out.println(player.getName());
//                        break;
//                    }
//                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Ошибка в базе данных");
            }

        };
        ref2.addValueEventListener(v);

//        ref2.removeEventListener(v);

        if (players != null) {
            return players;
        }
        return null;
    }

    public Player requestData2() {
        return players;
    }

    public void putData(String nameGame, String namePlayer, String vector2, String level, int x, int y) {
        ref.child(nameGame).child(namePlayer).child("cords").setValue(vector2);
        ref.child(nameGame).child(namePlayer).child("level").setValue(level);
        ref.child(nameGame).child(namePlayer).child("appleX").setValue(x);
        ref.child(nameGame).child(namePlayer).child("appleY").setValue(y);
        ref.child(nameGame).child(namePlayer).child("name").setValue(namePlayer);
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

//                        System.out.println(nGame);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(v);
//        ref.removeEventListener(v);
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
//            ref.removeEventListener(v);
        } catch (Exception e) {

        }
        return cPg;
    }

    public int countPlayersGames2() {
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

    public Player test() {
        return players;
    }

    public void dispose() {
        ref.removeEventListener(v);
//        ref2.removeEventListener(v);
    }
}
