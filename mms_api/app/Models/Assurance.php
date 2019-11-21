<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\Pivot;

class Assurance extends Pivot
{
    public $incrementing=true;
    public $table='assurances';

    protected $fillable = [
        'garantie','prime','duree','date_debut','numero_police_assurance','portefeuille','date_echeance','date_effet','client_id','assure_id',
    ];

}
