<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\Pivot;

class Contrat extends Pivot
{
    public $incrementing=true;
    public $table='contrats';

    protected $fillable = [
        'numero_contrat','garantie','prime','duree','numero_police_assurance','date_debut','date_echeance','date_effet','fin','date_fin','valider','client_id','marchand_id','assure_id',
    ];
    
    public function marchand(){
        return $this->belongsTo('App\Models\Marchand','marchand_id');
    }
    
    public function client(){
        return $this->belongsTo('App\Models\Client','client_id');
    }

    public function assure(){
        return $this->belongsTo('App\Models\Assure','assure_id');
    }

    public function portefeuilles(){
        return $this->hasMany('App\Models\Portefeuille','contrat_id','id');
    }

    public function beneficiaires(){
        return $this->belongsToMany('App\Models\Beneficiaire','benefices','contrat_id','beneficiaire_id')->using('App\Models\Benefice')->withPivot([
            'statut','taux',
        ])->withTimestamps();
    }

    public function documents(){
        return $this->morphMany('App\Models\Document','documentable');
    }
}
