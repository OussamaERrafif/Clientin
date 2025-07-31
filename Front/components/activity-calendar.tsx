"use client"

import { useState, useEffect } from "react"

interface ActivityCalendarProps {
  employeeName: string
}

export function ActivityCalendar({ employeeName }: ActivityCalendarProps) {
  const [activityData, setActivityData] = useState<number[]>([])

  useEffect(() => {
    // Generate random activity data for the past year (365 days)
    const data = Array.from({ length: 365 }, () => Math.floor(Math.random() * 5))
    setActivityData(data)
  }, [employeeName])

  const getActivityColor = (level: number) => {
    switch (level) {
      case 0:
        return "bg-gray-800"
      case 1:
        return "bg-green-900"
      case 2:
        return "bg-green-700"
      case 3:
        return "bg-green-500"
      case 4:
        return "bg-green-300"
      default:
        return "bg-gray-800"
    }
  }

  const getTooltipText = (level: number, index: number) => {
    const date = new Date()
    date.setDate(date.getDate() - (364 - index))
    const dateStr = date.toLocaleDateString("fr-FR")

    if (level === 0) return `Aucune activité le ${dateStr}`
    return `${level} ${level === 1 ? "interaction" : "interactions"} le ${dateStr}`
  }

  // Group days into weeks
  const weeks = []
  for (let i = 0; i < activityData.length; i += 7) {
    weeks.push(activityData.slice(i, i + 7))
  }

  return (
    <div className="bg-gray-800 p-6 rounded-lg">
      <div className="mb-4">
        <h4 className="text-white font-medium mb-2">Activité de {employeeName} au cours de l'année</h4>
        <p className="text-gray-400 text-sm">
          {activityData.filter((level) => level > 0).length} jours d'activité au cours des 12 derniers mois
        </p>
      </div>

      <div className="flex gap-1 overflow-x-auto">
        {weeks.map((week, weekIndex) => (
          <div key={weekIndex} className="flex flex-col gap-1">
            {week.map((level, dayIndex) => (
              <div
                key={`${weekIndex}-${dayIndex}`}
                className={`w-3 h-3 rounded-sm ${getActivityColor(level)} hover:ring-2 hover:ring-purple-400 cursor-pointer transition-all`}
                title={getTooltipText(level, weekIndex * 7 + dayIndex)}
              />
            ))}
          </div>
        ))}
      </div>

      <div className="flex items-center justify-between mt-4 text-xs text-gray-400">
        <span>Moins</span>
        <div className="flex gap-1">
          {[0, 1, 2, 3, 4].map((level) => (
            <div key={level} className={`w-3 h-3 rounded-sm ${getActivityColor(level)}`} />
          ))}
        </div>
        <span>Plus</span>
      </div>
    </div>
  )
}
