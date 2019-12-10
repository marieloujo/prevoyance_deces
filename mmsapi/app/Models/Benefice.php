<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Relations\Pivot;

class Benefice extends Pivot
{
    public $incrementing=true;
    public $table='benefices';

    protected $fillable = [
        'statut','taux','beneficiaire_id','contrat_id',
    ];

    
    public function contrat(){
        return $this->belongsTo('App\Models\Contrat','contrat_id');
    }
    
    public function beneficiaire(){
        return $this->belongsTo('App\Models\Beneficiaire','beneficiaire_id');
    }
}
