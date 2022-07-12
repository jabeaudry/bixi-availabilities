from urllib.request import urlopen
# import json
import json

df1 = spark.read.json(
    'https://gbfs.velobixi.com/gbfs/en/station_information.json')
df1.dtypes

df2 = spark.read.json('https://gbfs.velobixi.com/gbfs/en/station_status.json')
df2.dtypes