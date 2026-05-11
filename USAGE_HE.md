# מדריך שימוש - פרויקט Bricker

מדריך מהיר לעבודה עם הפרויקט וסנכרון אוטומטי עם GitHub.

## 📂 איפה הפרויקט

| מיקום | מה זה |
|---|---|
| `C:\Dvir\oop\ex2\Bricker\` | תיקיית הפרויקט המקומי |
| https://github.com/dvir-drori/bricker-game | הריפוזיטורי ב-GitHub (מסונכרן אוטומטית) |

## 🚀 פתיחת הפרויקט ב-IntelliJ

1. פתח את IntelliJ IDEA
2. **File → Open** → בחר את התיקייה `C:\Dvir\oop\ex2\Bricker`
3. אם IntelliJ שואל על Git roots — אשר
4. ודא ש-`DanoGameLab.jar` מקושר ב: **File → Project Structure → Libraries**

## ✏️ איך לערוך קוד ולסנכרן ל-GitHub

### זרימת עבודה רגילה (השינוי שלך עולה אוטומטית)

1. **ערוך** את הקובץ הרצוי ב-IntelliJ ושמור (`Ctrl+S`)
2. הקש **`Ctrl+K`** — נפתח חלון Commit
3. סמן את הקבצים שברצונך לכלול
4. כתוב הודעה קצרה ב-"Commit Message" (לדוגמה: `הוספתי PuckCollisionStrategy`)
5. לחץ **Commit** (לא Commit and Push — רק Commit!)
6. ✨ **ה-Hook הפנימי דוחף אוטומטית ל-GitHub**

תוכל לראות בחלון התחתון `[auto-sync] pushing to GitHub...` — זה אישור שהדחיפה הצליחה.

### לבדוק שזה עלה ל-GitHub
פתח דפדפן: https://github.com/dvir-drori/bricker-game  
תראה את ה-commit האחרון בראש העמוד.

## ⬇️ למשוך שינויים מ-GitHub (אם ערכת ממחשב אחר)

הקש **`Ctrl+T`** ב-IntelliJ → "Update Project" → לחץ OK.  
כל שינוי שבוצע ב-GitHub יורד אוטומטית למחשב המקומי.

## 🔧 שאלות נפוצות

### שיניתי קובץ אבל לא רואה שינוי ב-GitHub?
- ודא שלחצת **Commit** (לא רק שמרת)
- ודא שהקובץ סומן ב-checkbox בחלון ה-Commit
- בדוק שאין הודעת שגיאה ב-Console של IntelliJ

### אני רוצה לבטל את הדחיפה האוטומטית
מחק את הקובץ:
```
C:\Dvir\oop\ex2\Bricker\.git\hooks\post-commit
```
מעכשיו תצטרך לחיצה ידנית: `Ctrl+Shift+K` לדחיפה.

### אני רוצה להפעיל מחדש את הדחיפה האוטומטית
צור מחדש את הקובץ `C:\Dvir\oop\ex2\Bricker\.git\hooks\post-commit` עם התוכן:
```sh
#!/bin/sh
echo "[auto-sync] pushing to GitHub..."
git push origin HEAD 2>&1 | tail -3
```

### יש לי קונפליקט (Conflict)
זה קורה אם ערכת את אותו קובץ גם מקומית וגם ב-GitHub (או ממחשב אחר).
1. IntelliJ יפתח חלון "Merge Conflicts"
2. בחר איזה גרסה לשמור (יש, להם, או שילוב)
3. שמור ו-Commit שוב

### הריצה לא עובדת — חסר Library
ודא ש-`DanoGameLab.jar` קיים אצלך מקומית ושהוספת אותו ב:
**File → Project Structure → Libraries → + → Java → בחר את ה-jar**

ה-jar אינו נדחף ל-GitHub כי הוא חומר קורס.

## 📁 קבצים שכן ולא נדחפים ל-GitHub

✅ **כן נדחפים**: כל קבצי ה-Java, ה-README, ה-assets (תמונות וצלילים), ה-.gitignore  
❌ **לא נדחפים**: `out/`, `.idea/`, `*.iml`, `*.jar`, קבצי IDE

## 🆘 פקודות חירום (במסוף - אם משהו השתבש)

```bash
# בדיקת מצב
cd /c/Dvir/oop/ex2/Bricker
git status

# לראות את ההיסטוריה
git log --oneline -10

# לבטל שינוי מקומי שעדיין לא עשית Commit
git checkout -- שם_הקובץ

# למשוך הכל מ-GitHub ולדרוס שינויים מקומיים
git fetch origin
git reset --hard origin/main
```

## 👤 פרטי הריפוזיטורי

- **בעלים**: dvir-drori
- **שם הריפו**: bricker-game
- **Branch ראשי**: main
- **קישור**: https://github.com/dvir-drori/bricker-game

---

נכתב כחלק מההתקנה האוטומטית של הסנכרון.
