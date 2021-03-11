#include "select.h"

int main(int argc,char** argv) {
    if (sodium_init() == -1) {
        return 1;
    }
    srand(time(NULL));

    //Select(argc, argv);
    randomSelect(argc,argv);

    return 0;
}