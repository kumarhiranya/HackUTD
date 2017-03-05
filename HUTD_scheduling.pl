read_file(Stream,[]) :-
    at_end_of_stream(Stream).

read_file(Stream,[X|L]) :-
    \+ at_end_of_stream(Stream),
    read(Stream,X),
    read_file(Stream,L).


 :- use_module(library(clpfd)).
     
 schedule(Ss, End) :-
		 Ss = [SA1,SA2,SA3,SA4,16,SA5,SA6,SB1,SB2,SC1,SC2],
		 Es = [E1,E2,E3,E4,17,E5,E10,E6,E7,E8,E9],
		
		 Tasks = [task(SA1,4,E1, 1,0),
				  task(SA2,4,E2, 1,1),
				  task(SA3,4,E3, 1,2),
				  task(SA4,4,E4, 1,3),
				 % task(16, 1,17, 3,11),
				  task(SA5,4,E5, 1,9),
				  task(SA6,4,E10,1,10),
				  task(SB1,5,E6, 2,5),
				  task(SB2,5,E7, 2,6),
				  task(SC1,6,E8, 3,7),
				  task(SC2,6,E9, 3,8)],
		 domain(Ss, 9, 50),
		 domain(Es, 9, 51),
		 domain([End], 1,51),
		 maximum(End, Es),
		 cumulative(Tasks, [limit(3)]),
		 append(Ss, [End], Vars),
		 labeling([minimize(End)], Vars). % label End last
 
 %% End of file
 
 %| ?- schedule(Ss, End).
 %Ss = [1,17,10,10,5,5,1],
 %End = 23