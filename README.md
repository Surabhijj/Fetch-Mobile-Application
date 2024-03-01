Fetch Mobile Application

This is a Android application developed completly in Kotlin to fetch data from https://fetch-hiring.s3.amazonaws.com/hiring.json.
The Fetched data is displayed according to the following requirement to
-> Display all the items grouped by "listId"
-> Sort the results first by "listId" then by "name" when displaying.
-> Filter out any items where "name" is blank or null.

To Complete the First requirement I have provided a button on the top of the Application called group by ListID on click of which the retrieved data from the JSOn file is grouped according to their respective List ID and the Items which has same List ID are grouped using groupBy function and the data is displayed as follows-
 ![image](https://github.com/Surabhijj/Fetch-Mobile-Application/assets/73160422/c9d41484-ee84-45b7-bfb6-ed2f787b268c)
