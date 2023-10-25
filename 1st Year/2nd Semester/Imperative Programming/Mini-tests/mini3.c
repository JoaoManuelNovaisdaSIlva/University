#include <stdlib.h>
#include <stdio.h>

int hash (char s[]) {
    int r = 0;
    while (*s) {
        r += *s;
        s++;
        
    }
    return r;
}

int next(char s[], int n) {
    while (n > 0) {
        n--;
        if (s[n] < 'z') {
            if (s[n] == 'Z') s[n] = 'a';
            else s[n]++;
            return 1;
        } else {
            s[n] = 'A';
        }
    }
    return 0;
}

int main () {
    char s[10];
    int n,i;
    for (n=0;n<10;n++) {
        for (i=0;i<n;i++) s[i] = 'A';
        s[n] = '\0';
        do {
            int h = hash(s);
            if (h == 516) {
                printf("%s\n", s);
                fflush(stdout);
                return 0;
            }
        } while(next(s,n));
    }
    return 0;
}