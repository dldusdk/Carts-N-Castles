package seng201.team0.services;

import java.util.*;

public class CartPathFinder {
    private ArrayList<ArrayList<Integer>>tileList;
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public CartPathFinder(ArrayList<ArrayList<Integer>>setTileList){
        tileList = setTileList;

        boolean pathSolved = false;
        int start_col = 0;
        int start_row = 10;



        //while(!pathSolved){
            //int start_col + 1;
            //int next_row = start_row + 1;}

        //working on this, ignore prints
        for(ArrayList<Integer> innerList: tileList) {
            for (int num : innerList) {
                if(num!=-1){
                int current_col = innerList.indexOf(num);
                int current_row = tileList.indexOf(innerList);
                System.out.println("Value " + num + " row = " + current_row + "," + "column = " + current_col);}

            }}}




        }



