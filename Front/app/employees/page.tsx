"use client"

import { useState } from "react"
import { Card, CardContent } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Input } from "@/components/ui/input"
import { Search, Plus, Mail, Phone } from "lucide-react"
import { EmployeeModal } from "@/components/employee-modal"

const employees = [
  {
    id: 1,
    name: "Mohamed Benali",
    role: "Manager",
    department: "Service Client",
    status: "active",
    avatar: "/placeholder.svg?height=60&width=60&text=MB",
    email: "mohamed.benali@clientin.com",
    phone: "+212 6 12 34 56 78",
    rating: 4.8,
    feedbackCount: 156,
  },
  {
    id: 2,
    name: "Sarah Khalil",
    role: "Agent",
    department: "Support",
    status: "active",
    avatar: "/placeholder.svg?height=60&width=60&text=SK",
    email: "sarah.khalil@clientin.com",
    phone: "+212 6 87 65 43 21",
    rating: 4.6,
    feedbackCount: 89,
  },
  {
    id: 3,
    name: "Ahmed Tazi",
    role: "Superviseur",
    department: "Qualité",
    status: "away",
    avatar: "/placeholder.svg?height=60&width=60&text=AT",
    email: "ahmed.tazi@clientin.com",
    phone: "+212 6 11 22 33 44",
    rating: 4.9,
    feedbackCount: 203,
  },
  {
    id: 4,
    name: "Fatima Alami",
    role: "Agent",
    department: "Service Client",
    status: "offline",
    avatar: "/placeholder.svg?height=60&width=60&text=FA",
    email: "fatima.alami@clientin.com",
    phone: "+212 6 55 66 77 88",
    rating: 4.4,
    feedbackCount: 67,
  },
]

export default function EmployeesPage() {
  const [selectedEmployee, setSelectedEmployee] = useState<(typeof employees)[0] | null>(null)
  const [searchTerm, setSearchTerm] = useState("")
  const [statusFilter, setStatusFilter] = useState<"all" | "active" | "away" | "offline">("all")

  const filteredEmployees = employees.filter((employee) => {
    const matchesSearch =
      employee.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      employee.role.toLowerCase().includes(searchTerm.toLowerCase())
    const matchesStatus = statusFilter === "all" || employee.status === statusFilter
    return matchesSearch && matchesStatus
  })

  const getStatusColor = (status: string) => {
    switch (status) {
      case "active":
        return "bg-green-500"
      case "away":
        return "bg-yellow-500"
      case "offline":
        return "bg-gray-500"
      default:
        return "bg-gray-500"
    }
  }

  const getStatusText = (status: string) => {
    switch (status) {
      case "active":
        return "Actif"
      case "away":
        return "Absent"
      case "offline":
        return "Hors ligne"
      default:
        return "Inconnu"
    }
  }

  const getDepartmentColor = (department: string) => {
    switch (department) {
      case "Service Client":
        return "bg-blue-600 text-blue-100"
      case "Support":
        return "bg-green-600 text-green-100"
      case "Qualité":
        return "bg-purple-600 text-purple-100"
      default:
        return "bg-gray-600 text-gray-100"
    }
  }

  return (
    <div className="flex-1 bg-black p-6">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-3xl font-bold text-white">Employés</h1>
        <Button className="bg-purple-600 hover:bg-purple-700">
          <Plus className="h-4 w-4 mr-2" />
          Ajouter un employé
        </Button>
      </div>

      {/* Filters */}
      <div className="flex gap-4 mb-6">
        <div className="relative flex-1 max-w-md">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 h-4 w-4" />
          <Input
            placeholder="Rechercher un employé..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-10 bg-gray-900 border-gray-700 text-white"
          />
        </div>
        <div className="flex gap-2">
          {["all", "active", "away", "offline"].map((status) => (
            <Button
              key={status}
              variant={statusFilter === status ? "default" : "outline"}
              size="sm"
              onClick={() => setStatusFilter(status as any)}
              className={
                statusFilter === status
                  ? "bg-purple-600 hover:bg-purple-700"
                  : "border-gray-600 text-gray-300 hover:bg-gray-800 bg-transparent"
              }
            >
              {status === "all" ? "Tous" : getStatusText(status)}
            </Button>
          ))}
        </div>
      </div>

      {/* Employee Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {filteredEmployees.map((employee) => (
          <Card
            key={employee.id}
            className="bg-gray-900 border-gray-800 hover:border-purple-600 transition-colors cursor-pointer"
            onClick={() => setSelectedEmployee(employee)}
          >
            <CardContent className="p-6">
              <div className="flex items-center gap-4 mb-4">
                <div className="relative">
                  <Avatar className="h-16 w-16">
                    <AvatarImage src={employee.avatar || "/placeholder.svg"} />
                    <AvatarFallback>
                      {employee.name
                        .split(" ")
                        .map((n) => n[0])
                        .join("")}
                    </AvatarFallback>
                  </Avatar>
                  <div
                    className={`absolute -bottom-1 -right-1 h-5 w-5 rounded-full border-2 border-gray-900 ${getStatusColor(employee.status)}`}
                  />
                </div>
                <div className="flex-1">
                  <h3 className="text-white font-semibold">{employee.name}</h3>
                  <p className="text-gray-400 text-sm">{employee.role}</p>
                  <Badge className={`mt-1 text-xs ${getDepartmentColor(employee.department)}`}>
                    {employee.department}
                  </Badge>
                </div>
              </div>

              <div className="space-y-2 text-sm">
                <div className="flex items-center gap-2 text-gray-400">
                  <Mail className="h-4 w-4" />
                  <span className="truncate">{employee.email}</span>
                </div>
                <div className="flex items-center gap-2 text-gray-400">
                  <Phone className="h-4 w-4" />
                  <span>{employee.phone}</span>
                </div>
              </div>

              <div className="flex items-center justify-between mt-4 pt-4 border-t border-gray-800">
                <div className="text-center">
                  <div className="text-white font-semibold">{employee.rating}</div>
                  <div className="text-gray-400 text-xs">Note</div>
                </div>
                <div className="text-center">
                  <div className="text-white font-semibold">{employee.feedbackCount}</div>
                  <div className="text-gray-400 text-xs">Avis</div>
                </div>
                <Badge
                  className={
                    employee.status === "active"
                      ? "bg-green-600"
                      : employee.status === "away"
                        ? "bg-yellow-600"
                        : "bg-gray-600"
                  }
                >
                  {getStatusText(employee.status)}
                </Badge>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* Employee Modal */}
      {selectedEmployee && <EmployeeModal employee={selectedEmployee} onClose={() => setSelectedEmployee(null)} />}
    </div>
  )
}
