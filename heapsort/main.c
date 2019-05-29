#include<stdio.h>

void create(int []);
void down_adjust(int [],int);

void main()
{

    int ln,n,i,heap[i], last, temp;
    FILE *f1, *f2;

    f1 = fopen("data.txt", "r");
    f2 = fopen("data.out", "w"); //create data.out

    char line[1024];
    n = 0;
    while( fgets(line,sizeof(line),f1) != NULL)
       ln++; // n = number of lines from the file

    fseek(f1, 0, SEEK_SET);

    for (i=0; i<n; i++)
        fscanf(f1,"%d", &heap[i]); //reading the array from data.in


    //create a heap
    heap[0]=n;
    create(heap);

    //sorting
    while(heap[0] > 1)
    {
        //swap heap[1] and heap[last]
        last=heap[0];
        temp=heap[1];
        heap[1]=heap[last];
        heap[last]=temp;
        heap[0]--;
        down_adjust(heap,1);
    }

    //print sorted data

    for (i=0; i<n; i++)
        fprintf(f2, "%d\n", heap[i]); // print the array into data.out

    fclose(f1);
    fclose(f2);

    printf("zrobione 10/10\n");

}

void create(int heap[])
{
    int i,n;
    n=heap[0]; //no. of elements
    for(i=n/2;i>=1;i--)
        down_adjust(heap,i);
}

void down_adjust(int heap[],int i)
{
    int j,temp,n,flag=1;
    n=heap[0];

    while(2*i<=n && flag==1)
    {
        j=2*i;    //j points to left child
        if(j+1<=n && heap[j+1] > heap[j])
            j=j+1;
        if(heap[i] > heap[j])
            flag=0;
        else
        {
            temp=heap[i];
            heap[i]=heap[j];
            heap[j]=temp;
            i=j;
        }
    }
}
