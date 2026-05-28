
# Project Title

A brief description of what this project does and who it's for

Parfait 👍 je te fais un **doc clair, simple et pro** que tu peux envoyer direct à ton équipe.

---

# 📄 DOCUMENT PROJET — NoGravity

## 🎯 But du jeu

Le joueur contrôle un vaisseau qui se déplace horizontalement et doit détruire des ennemis pour gagner des points.

* Victoire : atteindre un certain score
* Défaite : perdre les 3 vies

---

# 🧱 Structure du projet

## 📁 `org.example`

Contient le cœur du jeu

### 🔹 `Main`

* Lance le jeu
* Crée la fenêtre
* Démarre la boucle principale

---

### 🔹 `GamePanel`

Classe principale du jeu

👉 Gère :

* La boucle de jeu (update + draw)
* Les ennemis
* Les bonus
* Les collisions
* Le score
* Le game over

👉 Méthodes importantes :

* `update()` → met à jour tout le jeu
* `paintComponent()` → affiche tout
* `checkCollisions()` → gère toutes les collisions
* `spawnEnemies()` → crée les ennemis
* `spawnBonus()` → crée les bonus
* `restartGame()` → reset la partie

---

### 🔹 `KeyHandler`

* Gère les touches clavier
* Déplacement gauche/droite
* Restart avec R

---

# 📁 `entity`

## 🔹 `Player`

Représente le joueur

👉 Gère :

* Position
* Déplacement
* Tir automatique
* Vie
* Bonus de tir

👉 Méthodes importantes :

* `update()` → mouvement + tir
* `draw()` → affichage
* `getBounds()` → collision
* `addLife()` → ajoute de la vie

---

## 🔹 `Enemy`

Représente un ennemi

👉 Gère :

* Descente verticale
* Affichage
* Collision

---

## 🔹 `Projectile`

Représente un tir

👉 Gère :

* Mouvement vers le haut
* Collision
* Affichage

---

## 🔹 `Bonus`

Représente un bonus

👉 Types :

* 0 → Vie ❤️
* 1 → Tir rapide ⚡

👉 Gère :

* Descente
* Effet sur le joueur
* Collision

---

# 📁 `system`

## 🔹 `ScoreManager`

* Gère le score du joueur
* Ajoute des points

---

# 🎮 Fonctionnalités actuelles

✔ Déplacement horizontal
✔ Tir automatique
✔ Ennemis qui spawn
✔ Collisions (tir / joueur / ennemis)
✔ Système de score
✔ Système de vies (3 vies)
✔ Game Over
✔ Restart avec touche R
✔ Bonus :

* Vie
* Tir rapide temporaire

---

# ⚙️ Fonctionnement global

1. Le jeu tourne en boucle (`GamePanel.run`)
2. `update()` met à jour :

   * joueur
   * ennemis
   * bonus
   * collisions
3. `paintComponent()` affiche tout

---

# ⚠️ Règles importantes pour l’équipe

👉 **1. Ne pas modifier GamePanel sans prévenir**
→ C’est le cœur du jeu

👉 **2. Toute logique passe par update()**
→ Pas de logique dans draw()

👉 **3. Respecter les classes**

* Player = joueur uniquement
* Enemy = ennemis
* Bonus = bonus

👉 **4. Utiliser les hitbox (`getBounds()`) pour collisions**

---

# 🚀 Améliorations possibles

👉 Gameplay :

* Nouveaux types d’ennemis
* Tir multiple
* Boss

👉 Bonus :

* Bouclier
* Double tir
* Vitesse

👉 Interface :

* Menu principal
* Interface de score améliorée

👉 Système avancé :

* Shop avec points 💰
* Niveaux

---

# 👑 Organisation équipe (important)

👉 Chaque personne peut travailler sur :

* Joueur → amélioration tir / animation
* Ennemis → nouveaux comportements
* Bonus → nouveaux effets
* UI → menus / affichage

---

# ✅ Conclusion

Le projet est :

* Fonctionnel ✔
* Structuré ✔
* Prêt à être amélioré ✔

---

Si tu veux aller plus loin 👇
👉 je peux te faire un **doc encore plus stylé (PDF à rendre)**
👉 ou t’aider à ajouter le **shop (gros point pour ton projet)**
