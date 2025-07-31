"use client"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Progress } from "@/components/ui/progress"
import { Mail, Phone, Calendar, MapPin, Star } from "lucide-react"
import { ActivityCalendar } from "@/components/activity-calendar"

interface Employee {
  id: number
  name: string
  role: string
  department: string
  status: string
  avatar: string
  email: string
  phone: string
  rating: number
  feedbackCount: number
}

interface EmployeeModalProps {
  employee: Employee
  onClose: () => void
}

export function EmployeeModal({ employee, onClose }: EmployeeModalProps) {
  return (
    <Dialog open={true} onOpenChange={onClose}>
      <DialogContent className="max-w-5xl max-h-[90vh] overflow-y-auto bg-gray-900 border-gray-800">
        <DialogHeader>
          <DialogTitle className="text-white">Profil Employé</DialogTitle>
        </DialogHeader>

        <div className="space-y-6">
          {/* Employee Header */}
          <div className="flex items-center gap-6 p-6 bg-gray-800 rounded-lg">
            <Avatar className="h-24 w-24">
              <AvatarImage src={employee.avatar || "/placeholder.svg"} />
              <AvatarFallback>
                {employee.name
                  .split(" ")
                  .map((n) => n[0])
                  .join("")}
              </AvatarFallback>
            </Avatar>
            <div className="flex-1">
              <h2 className="text-2xl font-bold text-white">{employee.name}</h2>
              <p className="text-gray-300">
                {employee.role} • {employee.department}
              </p>
              <div className="flex items-center gap-4 mt-2">
                <Badge className="bg-green-600">Actif</Badge>
                <div className="flex items-center gap-1 text-yellow-400">
                  <Star className="h-4 w-4 fill-current" />
                  <span>{employee.rating}</span>
                </div>
                <span className="text-gray-400">{employee.feedbackCount} avis</span>
              </div>
            </div>
          </div>

          <Tabs defaultValue="personal" className="w-full">
            <TabsList className="grid w-full grid-cols-5 bg-gray-800">
              <TabsTrigger value="personal" className="text-white">
                Personnel
              </TabsTrigger>
              <TabsTrigger value="hr" className="text-white">
                RH
              </TabsTrigger>
              <TabsTrigger value="performance" className="text-white">
                Performance
              </TabsTrigger>
              <TabsTrigger value="goals" className="text-white">
                Objectifs
              </TabsTrigger>
              <TabsTrigger value="activity" className="text-white">
                Activité
              </TabsTrigger>
            </TabsList>

            <TabsContent value="personal" className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Informations Personnelles</h3>
                  <div className="space-y-3">
                    <div className="flex items-center gap-3">
                      <Mail className="h-4 w-4 text-gray-400" />
                      <span className="text-gray-300">{employee.email}</span>
                    </div>
                    <div className="flex items-center gap-3">
                      <Phone className="h-4 w-4 text-gray-400" />
                      <span className="text-gray-300">{employee.phone}</span>
                    </div>
                    <div className="flex items-center gap-3">
                      <Calendar className="h-4 w-4 text-gray-400" />
                      <span className="text-gray-300">Embauché le 15 Mars 2023</span>
                    </div>
                    <div className="flex items-center gap-3">
                      <MapPin className="h-4 w-4 text-gray-400" />
                      <span className="text-gray-300">Casablanca, Maroc</span>
                    </div>
                  </div>
                </div>
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Informations Professionnelles</h3>
                  <div className="space-y-3">
                    <div>
                      <span className="text-gray-400">ID Employé:</span>
                      <span className="text-white ml-2">EMP-{employee.id.toString().padStart(4, "0")}</span>
                    </div>
                    <div>
                      <span className="text-gray-400">CIN:</span>
                      <span className="text-white ml-2">AB123456</span>
                    </div>
                    <div>
                      <span className="text-gray-400">CNSS:</span>
                      <span className="text-white ml-2">123456789</span>
                    </div>
                    <div>
                      <span className="text-gray-400">Type de contrat:</span>
                      <span className="text-white ml-2">CDI</span>
                    </div>
                  </div>
                </div>
              </div>
            </TabsContent>

            <TabsContent value="hr" className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Historique des Salaires</h3>
                  <div className="space-y-2">
                    <div className="flex justify-between p-3 bg-gray-800 rounded">
                      <span className="text-gray-300">Janvier 2024</span>
                      <span className="text-white">8,500 MAD</span>
                    </div>
                    <div className="flex justify-between p-3 bg-gray-800 rounded">
                      <span className="text-gray-300">Décembre 2023</span>
                      <span className="text-white">8,500 MAD</span>
                    </div>
                  </div>
                </div>
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Congés</h3>
                  <div className="space-y-3">
                    <div>
                      <div className="flex justify-between text-sm mb-1">
                        <span className="text-gray-300">Congés utilisés</span>
                        <span className="text-white">12/30 jours</span>
                      </div>
                      <Progress value={40} className="h-2" />
                    </div>
                    <div className="space-y-2">
                      <div className="flex justify-between p-2 bg-gray-800 rounded text-sm">
                        <span className="text-gray-300">Congé maladie</span>
                        <Badge variant="outline">Approuvé</Badge>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </TabsContent>

            <TabsContent value="performance" className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Évaluation Client</h3>
                  <div className="space-y-3">
                    <div className="flex items-center justify-between">
                      <span className="text-gray-300">Note moyenne</span>
                      <div className="flex items-center gap-2">
                        <Star className="h-4 w-4 text-yellow-400 fill-current" />
                        <span className="text-white font-semibold">{employee.rating}/5</span>
                      </div>
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-gray-300">Total des avis</span>
                      <span className="text-white">{employee.feedbackCount}</span>
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-gray-300">Avis positifs</span>
                      <span className="text-green-400">85%</span>
                    </div>
                  </div>
                </div>
                <div className="space-y-4">
                  <h3 className="text-lg font-semibold text-white">Badges</h3>
                  <div className="flex flex-wrap gap-2">
                    <Badge className="bg-yellow-600">Service d'excellence</Badge>
                    <Badge className="bg-blue-600">Client favori</Badge>
                    <Badge className="bg-green-600">Résolution rapide</Badge>
                  </div>
                </div>
              </div>
            </TabsContent>

            <TabsContent value="goals" className="space-y-4">
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-semibold text-white mb-4">Objectifs Actuels</h3>
                  <div className="space-y-4">
                    <div className="p-4 bg-gray-800 rounded-lg">
                      <div className="flex items-center justify-between mb-2">
                        <span className="text-white font-medium">Améliorer la satisfaction client</span>
                        <Badge className="bg-purple-600">En cours</Badge>
                      </div>
                      <Progress value={75} className="h-2 mb-2" />
                      <span className="text-gray-400 text-sm">75% complété</span>
                    </div>
                    <div className="p-4 bg-gray-800 rounded-lg">
                      <div className="flex items-center justify-between mb-2">
                        <span className="text-white font-medium">Formation en communication</span>
                        <Badge className="bg-green-600">Terminé</Badge>
                      </div>
                      <Progress value={100} className="h-2 mb-2" />
                      <span className="text-gray-400 text-sm">100% complété</span>
                    </div>
                  </div>
                </div>
              </div>
            </TabsContent>

            <TabsContent value="activity" className="space-y-4">
              <div>
                <h3 className="text-lg font-semibold text-white mb-4">Calendrier d'Activité</h3>
                <ActivityCalendar employeeName={employee.name} />
              </div>
            </TabsContent>
          </Tabs>
        </div>
      </DialogContent>
    </Dialog>
  )
}
