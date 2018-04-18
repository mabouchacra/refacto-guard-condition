## Scenario

* Si le header **`sm_user`** est absent, alors on renvoie **not-authorized**
* Si le header **`sm_user`** est présent mais vide, alors on renvoie **login**
* Si le header **`sm_user`** n'est pas vide alors :
  * Si le cookie est vide ou absent, alors on renvoie sur **login**
  * si le cookie est différent du header, alors on renvoie **login**
  * si le cookie est égal au header, alors on renvoie **ok**