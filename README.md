# Croqueteria

![Croqueteria Logo](imgs/croqueteria.png)
![Croqueteria Logo](imgs/croqueteria_2.png)

# Known limitations

* LLM reasoning is slow and works only 50% of the time. Should be replaced by a proper external service. ChatGPT works fine.
* average rating is calculated for every request that is not cached. For now that's fine, but as soon as the croquette numbers will rise (and they will!) this can be a bottleneck
  * Solution: Update the average rating upon new ratings (event-driven?)
