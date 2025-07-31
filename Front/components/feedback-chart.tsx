"use client"

import { Line, LineChart, XAxis, YAxis, ResponsiveContainer } from "recharts"

const data = [
  { name: "Sem 1", positive: 20, negative: 8 },
  { name: "Sem 2", positive: 35, negative: 12 },
  { name: "Sem 3", positive: 28, negative: 15 },
  { name: "Sem 4", positive: 45, negative: 10 },
  { name: "Sem 5", positive: 38, negative: 18 },
]

export function FeedbackChart() {
  return (
    <div className="h-64">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={data}>
          <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
          <YAxis axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
          <Line
            type="monotone"
            dataKey="positive"
            stroke="#10B981"
            strokeWidth={3}
            dot={{ fill: "#10B981", strokeWidth: 2, r: 4 }}
          />
          <Line
            type="monotone"
            dataKey="negative"
            stroke="#EF4444"
            strokeWidth={3}
            dot={{ fill: "#EF4444", strokeWidth: 2, r: 4 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}
