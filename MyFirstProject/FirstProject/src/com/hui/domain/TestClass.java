package com.hui.domain;

class TestClass {
    public static void main(String[] args) {
        String name="aajgajgjjahgaaaaaaadafagagafagaga.png";
        int i = name.hashCode();
        System.out.println(i);
        String hex = Integer.toHexString(i);
        System.out.println(hex);
        int j=hex.length();
        System.out.println(j);
        for(int k=0;k<8-j;k++){
            hex="0"+hex;
        }

        System.out.println("/"+hex.charAt(0)+"/"+hex.charAt(1)+"/"+hex.charAt(2)+"/"+hex.charAt(3)+"/"+hex.charAt(4)+"/"+hex.charAt(5)+"/"+hex.charAt(6)+"/"+hex.charAt(7));
    }

}
