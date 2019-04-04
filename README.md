
**My-TRAC Data Model**

***Activity***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| activity_id | string | yes | My-TRAC |
| activity_name | string | yes | crawled |
| activity_lat | string | yes | crawled |
| activity_lon | float | yes | crawled |
| activity_type | float | yes | crawled |
| time_start | timestamp (long) | yes | crawled |
| time_end | timestamp (long) | yes | crawled |

***Agency***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| agency_id | string | yes | operators platform/gtfs |
| agency_name | string | yes | gtfs |
| agency_url | string | yes | gtfs |
| agency_timezone | string | yes | gtfs |
| agency_lang | string | no | gtfs |
| agency_phone | string | no | crawled |
| agency_fare_url| string | no | crawled |
| descriptors | string | no | My-TRAC |

***Calendar_Date***

***Calendar***

***Facility***

***Feed_Info***

***Frequency***

***POI***

***Route***

***Shape***

***Stop_Time***

***Stop***

***Transfer***

***Trip***
