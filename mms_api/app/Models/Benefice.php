<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\Pivot;

class Benefice extends Pivot
{
    public $incrementing=true;
    public $table='benefices';

    protected $fillable = [
        'statut','taux','beneficiaire_id','assure_id',
    ];
}
