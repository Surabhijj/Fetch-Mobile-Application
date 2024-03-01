**Fetch Mobile Application**

This is a Android application developed completly in Kotlin to fetch data from https://fetch-hiring.s3.amazonaws.com/hiring.json.
The Fetched data is displayed according to the following requirement to

-> Display all the items grouped by "listId"

-> Sort the results first by "listId" then by "name" when displaying.

-> Filter out any items where "name" is blank or null.


To Complete the First requirement I have provided a button on the top of the Application called group by ListID on click of which the retrieved data from the JSON file is grouped with respect to List ID and the Items which has same List ID are grouped using groupBy function and the data is displayed as in fig 1.
Similary another button Sort By is a dropdown menu button which gives a dropdown menu to select the option to sort the list with respect to ID or LIst ID or Name depending on the requirement as shown in fig 2 and fig 3.


![Screenshot 2024-03-01 at 2 34 47 AM](https://github.com/Surabhijj/Fetch-Mobile-Application/assets/73160422/ecb65968-d569-43b9-b174-d806418fa9d7) - fig 1
 ![sortbyListID](https://github.com/Surabhijj/Fetch-Mobile-Application/assets/73160422/09ffac04-d438-41cb-953e-e90b4fcff168) - fig 2
 ![sortByName](https://github.com/Surabhijj/Fetch-Mobile-Application/assets/73160422/30b032b6-28e3-42cc-b4bd-9fd284061eb4) - fig 3


