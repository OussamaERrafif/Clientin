"use client"

import { useState, useEffect } from "react"
import { Line, LineChart, XAxis, YAxis, ResponsiveContainer, Tooltip } from "recharts"

const generateRandomData = () => {
  const days = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"]
  return days.map((day) => ({
    name: day,
    positive: Math.floor(Math.random() * 50) + 20,
    negative: Math.floor(Math.random() * 20) + 5,
  }))
}

export function DynamicFeedbackChart() {
  const [data, setData] = useState(generateRandomData())

  useEffect(() => {
    const interval = setInterval(() => {
      setData(generateRandomData())
    }, 5000) // Update every 5 seconds

    return () => clearInterval(interval)
  }, [])

  return (
    <div className="h-64">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart data={data}>
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
            animationDuration={1000}
          />
          <Line
            type="monotone"
            dataKey="negative"
            stroke="#EF4444"
            strokeWidth={3}
            dot={{ fill: "#EF4444", strokeWidth: 2, r: 4 }}
            animationDuration={1000}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}
