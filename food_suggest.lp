name(Aswin).
age(25).
sex(M).
mealcost(10).
hobbies([reading, running, cycling, swimming]).
food([Indian, mexican, italian]).
food_ava([panda, subway, chick, wendys, jack, whataburger]).
cal_ava([600, 350 , 450, 500, 400, 650]).
food_cost([10 , 7 , 5, 4, 8, 11]).
food_pref(0). %0 being economical pref and 1 being calorific pref

cost(panda, 10).
cost(subway, 7).
cost(chick, 5).
cost(wendys, 4).
cost(jack, 8).
cost(whataburger, 11).

cal(panda, 600).
cal(subway, 350).
cal(chick, 450).
cal(wendys, 500).
cal(jack, 400).
cal(whataburger, 650).

height(150).
weight(85).
location(75080).
sleepHours(8).
lifestyle(1).
daily_c(0).


bmi(weight(X), height(Y), Z):- Z is X/(Y*Y/10000).

%status_body(Z, K):- 18<Z, Z<25, K is normal=0.
%status_body(Z, K):- Z<19, K is underweight=1.
%status_body(Z, K):- 24<Z, K is obese=2.

bubble_sort(List,Sorted):-b_sort(List,[],Sorted).
b_sort([],Acc,Acc).
b_sort([H|T],Acc,Sorted):-bubble(H,T,NT,Max),b_sort(NT,[Max|Acc],Sorted).


status_body(X,Y, K, C, R):- Z is X/(Y*Y/10000),18<Z, Z<25, K is 0, C is 1200, daily_c(A),R is C-A.
status_body(X,Y, K, C, R):- Z is X/(Y*Y/10000),Z<19, K is 1 , C is 1000, daily_c(A),R is C-A.
status_body(X,Y, K, C, R):- Z is X/(Y*Y/10000),24<Z, K is 2 , C is 800, daily_c(A),R is C-A.

isort(L,S):- isort(L,[],S).
isort([],P,P).
isort([H|T], P, R):- insert(H,P,Pr), isort(T,Pr,R).
insert(H,[],[H]).
insert(H,[X|T],[H,X|T]) :- H =< X.
insert(H,[X|T],[X|R]):- H>X, insert(H,T,R).

disort(L,S):- disort(L,[],S).
disort([],P,P).
disort([H|T], P, R):- dinsert(H,P,Pr), disort(T,Pr,R).
dinsert(H,[],[H]).
dinsert(H,[X|T],[H,X|T]) :- H > X.
dinsert(H,[X|T],[X|R]):- H<X, dinsert(H,T,R).

%read_file(Stream,[]) :-
%    at_end_of_stream(Stream).

%read_file(Stream,[X|L]) :-
 %   \+ at_end_of_stream(Stream),
  %  read(Stream,X),
   % read_file(Stream,L).

recommend(R,A):-  food_pref(0), food_cost([X|T]), isort([X|T], M), member(Z,M), cost(R,Z), cal(R, C),H is 800,C<H,A is H-C.
recommend(R,A):- not food_pref(0), food_cost([X|T]), disort([X|T], M), member(Z,M), cost(R,Z), cal(R, C),H is 800,C<H,A is H-C.
%write(Lines) :- not recommend(R,A), Lines is 'Random Stuff'.

member(Z,[_|T]):- member(Z,T).
member(Z,[Z|_]).

?-recommend(R,A).
?-status_body(65,163, K, C, R).