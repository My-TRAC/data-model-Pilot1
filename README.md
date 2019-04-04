
**My-TRAC Data Model**

***Data Types***

| Type name | Description |
| --------- | ----------- |
| int | 32-bit integer |
| long | 64-bit integer |
| float | 32-bit floating point number |
| string | variable length string |
| timestamp | long containing epoch time in seconds |
| time | string with format "HH:MM:SS" |
| date | int with the form YYYYMMDD. Example 20180130 |
| enum | int representing an enumeration. Example 0 for bus, 1 for railway, etc. |

***Activity***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| activity_id | string | yes | My-TRAC |
| activity_name | string | yes | crawled |
| activity_lat | string | yes | crawled |
| activity_lon | float | yes | crawled |
| activity_type | string | yes | crawled |
| time_start | timestamp | yes | crawled |
| time_end | timestamp | yes | crawled |
| descriptors | string | no | My-TRAC |

***Agency***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| agency_id | string | yes | operators platform/gtfs |
| agency_name | string | yes | operators platform/gtfs |
| agency_url | string | yes | operators platform/gtfs |
| agency_timezone | string | yes | operators platform/gtfs |
| agency_lang | string | no | operators platform/gtfs |
| agency_phone | string | no | operators platform/gtfs |
| agency_fare_url| string | no | operators platform/gtfs |
| descriptors | string | no | My-TRAC |

***Calendar_Date***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| service_id | string | yes | operators platform/gtfs |
| date | date | yes | operators platform/gtfs |
| exception_type| enum | yes | operators platform/gtfs |

***Calendar***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| service_id | string | yes | operators platform/gtfs |
| monday | int | yes | operators platform/gtfs |
| tuesday | int | yes | operators platform/gtfs |
| wednesday | int | yes | operators platform/gtfs |
| thursday | int | yes | operators platform/gtfs |
| friday | int | yes | operators platform/gtfs |
| saturday | int | yes | operators platform/gtfs |
| sunday | int | yes | operators platform/gtfs |
| start_date | date | yes | operators platform/gtfs |
| end_date | date | yes | operators platform/gtfs |


***Facility***

| Field Name | Field Type | Required | Source |
| ---------- | ---------- | -------- | ------ |
| facility_id | string | yes | My-TRAC |
| facility_name | string | yes | crawled |
| facility_lat | string | yes | crawled |
| facility_lon | float | yes | crawled |
| facility_type | string | yes | crawled |
| descriptors | string | no | My-TRAC |

***Feed_Info***

***Frequency***

***POI***

***Route***

***Shape***

***Stop_Time***

***Stop***

***Transfer***

***Trip***
