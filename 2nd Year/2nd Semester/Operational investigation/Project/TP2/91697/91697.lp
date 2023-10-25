/* Objective function */
min: 1Xk_1 + 15Xk_2 + 2Xk_3 + 4Xk_4 + 6Xk_5 + 11Xk_6 + 9Xk_7 + 9Xk_8 + 9Xk_9 + 10Xk_10 + 0Xk_11
     13Xa_2 + 5Xa_3 + 5Xa_5 + 10Xa_6 + 7Xa_7 + 5Xa_8 + 0Xa_9 +7Xa_10 + 1Xa_11 + 10Xb_5 + 6Xb_7 +
     15Xb_11 + 11Xc_2 + 6Xc_5 + 6Xc_7 + 0Xc_8 + 6Xc_9 + 2Xc_11 + 14Xd_2 + 4Xd_5 +8Xd_6 + 8Xd_7 + 8Xd_8 +
     11 Xd_10 + 4Xd_11 + 6Xe_11 + 6Xf_5 + 5Xf_7 + 11Xf_11 + 4Xg_5 + 9Xg_11 + 6Xh_5 + 10Xh_7 + 9Xh_11 + 0Xi_1 +
     13Xi_2 + 5Xi_3 + 5Xi_5 + 10Xi_6 + 7Xi_7 + 5Xi_8 + 7xi_10 + 9Xi_11 + 4Xj_2 + 7Xj_5 +5Xj_7 + 6Xj_8 + 10Xj_11;
     
Xk_1 + Xk_2 + Xk_3 + Xk_4 + Xk_5 + Xk_6 + Xk_7 + Xk_8 + Xk_9 + Xk_10 + Xk_11 = 3 ;
Xa_2 + Xa_3 + Xa_5 + Xa_6 + Xa_7 + Xa_8 + Xa_9 + Xa_10 + Xa_11 = 1 ;
Xb_5 + Xb_7 + Xb_11 = 1 ;
Xc_2 + Xc_5 + Xc_7 + Xc_8 + Xc_9 + Xc_11 = 1 ;
Xd_2 + Xd_5 + Xd_6 + Xd_7 + Xd_8 +  Xd_10 + Xd_11 = 1 ;
Xe_11 = 1 ;
Xf_5 + Xf_7 + Xf_11 = 1 ;
Xg_5 + Xg_11 = 1 ;
Xh_5 + Xh_7 + Xh_11 = 1 ;
Xi_1 + Xi_2 + Xi_3 + Xi_5 + Xi_6 + Xi_7 + Xi_8 + xi_10 + Xi_11 = 1 ;
Xj_2 + Xj_5 + Xj_7 + Xj_8 + Xj_11 = 1 ;
Xk_1 + Xi_1 = 1 ;
Xk_2 + Xa_2 + Xc_2 + Xd_2 + Xj_2 = 1 ;
Xk_3 + Xa_3 + Xi_3 = 1 ;
Xk_4 = 1 ;
Xk_5 + Xb_5 + Xc_5 + Xd_5 + Xf_5 + Xg_5 + Xh_5 + Xi_5 + Xj_5 = 1 ;
Xk_6 + Xa_6 + Xd_6 + Xi_6 = 1 ;
Xk_7 + Xa_7 + Xb_7 + Xc_7 + Xd_7 + Xf_7 + Xh_7 + Xi_7 + Xj_7 = 1 ;
Xk_8 + Xa_8 + Xc_8 + Xd_8 + Xi_8 + Xj_8 = 1 ;
Xk_9 + Xa_9 + Xc_9 = 1 ;
Xk_10 + Xa_10 + Xd_10 + Xi_10 = 1 ;
Xk_11 + Xa_11 + Xb_11 + Xc_11 + Xd_11 + Xe_11 + Xf_11 + Xg_11 + Xh_11 + Xi_11 + Xj_11 = 3 ;

Xe_11 >= 0 ;
Xk_1 >= 0;
Xk_2 >= 0;
Xk_3 >= 0;
Xk_4 >= 0;
Xk_5 >= 0;
Xk_6 >= 0;
Xk_7 >= 0;
Xk_8 >= 0;
Xk_9 >= 0;
Xk_10 >= 0 ;


Xa_2 >= 0;
Xa_3 >= 0;
Xa_5 >= 0;
Xa_6 >= 0;
Xa_7 >= 0;
Xa_8 >= 0;
Xa_9 >= 0;
Xa_10 >= 0;
Xa_11 >= 0;

Xb_5 >= 0;
Xb_7 >= 0;
Xb_11 >= 0;
Xc_2 >= 0;
Xc_5 >= 0;
Xc_7 >= 0;
Xc_8 >= 0;
Xc_9 >= 0;
Xc_11 >= 0;


Xd_2 >= 0;
Xd_5 >= 0;
Xd_6 >= 0;
Xd_7 >= 0;
Xd_8 >= 0;
Xd_10 >= 0;
Xd_11 >= 0 ;

Xf_5 >= 0;
Xf_7 >= 0;
Xf_11 >= 0;

Xg_5 >= 0;
Xg_11 >= 0;

Xh_7 >= 0;
Xh_11 >= 0;

Xi_1 >= 0;
Xi_2 >= 0;
Xi_3 >= 0;
Xi_5 >= 0;
Xi_6 >= 0;
Xi_7 >= 0;
Xi_8 >= 0;
Xi_10 >= 0;
Xi_11 >= 0;

Xj_2 >= 0;
Xj_5 >= 0;
Xj_7 >= 0;
Xj_8 >= 0;
Xj_11 >= 0;



/* Variable bounds */