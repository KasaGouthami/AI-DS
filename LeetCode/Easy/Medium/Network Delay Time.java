import java.util.*;

public class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            graph.computeIfAbsent(edge[0], x -> new ArrayList<>()).add(new int[]{edge[1], edge[2]});
        }

        // Min-heap priority queue to get the next closest node
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{k, 0}); // {node, time}

        // Map to store the shortest time to reach each node
        Map<Integer, Integer> dist = new HashMap<>();

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0], time = current[1];

            if (dist.containsKey(node)) continue;
            dist.put(node, time);

            if (!graph.containsKey(node)) continue;

            for (int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0], weight = neighbor[1];
                if (!dist.containsKey(nextNode)) {
                    pq.offer(new int[]{nextNode, time + weight});
                }
            }
        }

        if (dist.size() != n) return -1;

        int maxTime = 0;
        for (int time : dist.values()) {
            maxTime = Math.max(maxTime, time);
        }

        return maxTime;
    }
}
