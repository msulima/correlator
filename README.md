Command line tool for displaying statistical correlation of Graphite data series.

# Usage

```
sbt run "http://play.grafana.org/graphite/render?target=summarize(statsd.fakesite.timers.ads_timer.*%2C%20%2740min%27%2C%20%27avg%27)%2C%204&from=-2h&until=now&format=raw&maxDataPoints=956"
```
