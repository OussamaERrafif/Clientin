"use client"

import { Line, LineChart, Bar, BarChart, XAxis, YAxis, ResponsiveContainer, Tooltip } from "recharts"

const sentimentData = [
  { name: "Jan", positive: 65, negative: 15 },
  { name: "Fév", positive: 72, negative: 12 },
  { name: "Mar", positive: 68, negative: 18 },
  { name: "Avr", positive: 78, negative: 10 },
  { name: "Mai", positive: 85, negative: 8 },
  { name: "Jun", positive: 82, negative: 11 },
]

const volumeData = [
  { name: "Lun", value: 45 },
  { name: "Mar", value: 52 },
  { name: "Mer", value: 38 },
  { name: "Jeu", value: 61 },
  { name: "Ven", value: 55 },
  { name: "Sam", value: 28 },
  { name: "Dim", value: 22 },
]

interface InsightChartProps {
  type: "sentiment" | "volume"
}

export function InsightChart({ type }: InsightChartProps) {
  if (type === "sentiment") {
    return (
      <div className="h-64">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={sentimentData}>
            <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
            <YAxis axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
            <Tooltip
              contentStyle={{
                backgroundColor: "#1F2937",
                border: "1px solid #374151",
                borderRadius: "8px",
                color: "#F9FAFB",
              }}
            />
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

  return (
    <div className="h-64">
      <ResponsiveContainer width="100%" height="100%">
        <BarChart data={volumeData}>
          <XAxis dataKey="name" axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
          <YAxis axisLine={false} tickLine={false} tick={{ fill: "#9CA3AF", fontSize: 12 }} />
          <Tooltip
            contentStyle={{
              backgroundColor: "#1F2937",
              border: "1px solid #374151",
              borderRadius: "8px",
              color: "#F9FAFB",
            }}
          />
          <Bar dataKey="value" fill="#8B5CF6" radius={[4, 4, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}
