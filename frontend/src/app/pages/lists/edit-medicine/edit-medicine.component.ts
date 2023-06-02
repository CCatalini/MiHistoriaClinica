import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-edit-medicine',
  templateUrl: './edit-medicine.component.html',
  styleUrls: ['./edit-medicine.component.css']
})
export class EditMedicineComponent implements OnInit {
    medicine: any;

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private router: Router
    ) { }

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
            this.medicine = params['medicine'];
        });
    }

    saveMedicine() {
        this.http.put(`/updateMedicine/${this.medicine.id}`, this.medicine)
            .subscribe(
                (response) => {
                    console.log('Medicamento actualizado:', response);
                    // Redirigir al usuario a la página de lista de medicamentos después de guardar los cambios.
                    this.router.navigate(['/medic/medicines-list']);
                },
                (error) => {
                    console.error('Error al actualizar el medicamento:', error);
                    // Manejar el error de acuerdo a tus necesidades
                }
            );
    }
}